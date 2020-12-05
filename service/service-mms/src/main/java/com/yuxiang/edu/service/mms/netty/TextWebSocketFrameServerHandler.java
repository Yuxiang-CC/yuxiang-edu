package com.yuxiang.edu.service.mms.netty;

import com.alibaba.fastjson.JSON;
import com.yuxiang.edu.service.base.constant.VodConstant;
import com.yuxiang.edu.service.mms.enums.MsgActionEnum;
import com.yuxiang.edu.service.mms.utils.SpringUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: yuxiang
 * @Date: 2020/11/27 16:47
 */
public class TextWebSocketFrameServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /**
     * 定义一个channel，管理所有的channel
     * GlobalEventExecutor.INSTANCE 是一个全局事件执行器，是一个单例
     * 此处也可以自定义一个 集合存储所有的channel，但是比较麻烦，我们可以用netty提供的channelGroup
     */
    public static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 1.获取客户端传输消息
        String content = msg.text();
        // 2.判断消息类型，根据不同的类型来处理不同的业务
        DataContent dataContent = JSON.parseObject(content, DataContent.class);

        Channel channel = ctx.channel();

        Integer action = dataContent.getAction();
        if (MsgActionEnum.CONNECT.type.equals(action)) {
            ChatMessage chatMessage = dataContent.getChatMessage();
            String liveId = chatMessage.getLiveId();
            // 2.1 当websocket 第一次open时候，初始化channel，将channel与用户id关联
            boolean contains = MemberChannelRelation.contains(liveId);
            // 如果存在该直播间则直接将其关联
            if (contains) {
                System.out.println("加入直播间" + liveId + "(" + chatMessage.getMemberId() + ")");
                MemberChannelRelation.get(liveId).put(chatMessage.getMemberId(), channel);
            } else {
                // 直播间聊天室不存在，则查询该直播间 TODO【直播间关闭后是否还能聊天】
                System.out.println("初始化直播间号：" + liveId + "(" + chatMessage.getMemberId() + ")");
                ConcurrentHashMap<String, Channel> map = new ConcurrentHashMap<>(16);
                map.put(chatMessage.getMemberId(), channel);
                MemberChannelRelation.put(liveId, map);
            }

            // 将用户信息存储直播间对应信息 TODO 【是否需要存入缓存】
            RedisTemplate<String, Serializable> redisTemplate = (RedisTemplate<String, Serializable>) SpringUtils.getApplicationContext().getBean("redisTemplate");
            // 将信息存入缓存
            redisTemplate.opsForHash().put(VodConstant.LIVE_MEMBERS_INFO + chatMessage.getLiveId(), chatMessage.getMemberId(), chatMessage);

            System.out.println(liveId +"-人数：" + MemberChannelRelation.get(liveId).size());

        } else if (MsgActionEnum.CHAT.type.equals(action)) {
            ChatMessage chatMessage = dataContent.getChatMessage();
            String liveId = chatMessage.getLiveId();
            // 2.2 当用户发送消息时，分发给直播间其他用户
            if (MemberChannelRelation.contains(liveId)) {

                forwarding(liveId, chatMessage);
            }

        } else if (MsgActionEnum.KEEPALIVE.type.equals(action)) {

        } else if (MsgActionEnum.QUIT.type.equals(action)) {
            System.out.println("退出直播间！");
            // TODO 【退出直播间，清除Channel信息】
            ChatMessage chatMessage = dataContent.getChatMessage();
            String liveId = chatMessage.getLiveId();
            if (MemberChannelRelation.contains(liveId)) {
                ConcurrentHashMap<String, Channel> map = MemberChannelRelation.get(liveId);
                // 将用户信息从直播间移除
                map.remove(chatMessage.getMemberId());
                // 从缓存中移除信息
                RedisTemplate<String, Serializable> redisTemplate = (RedisTemplate<String, Serializable>) SpringUtils.getApplicationContext().getBean("redisTemplate");
                redisTemplate.opsForHash().delete(VodConstant.LIVE_MEMBERS_INFO + chatMessage.getLiveId(), chatMessage.getMemberId());
            }

        }
    }

    /**
     * 分发消息
     * @param liveId
     * @param msg
     */
    private static void forwarding(String liveId, ChatMessage msg) {


        ConcurrentHashMap<String, Channel> map = MemberChannelRelation.get(liveId);
        System.out.println(liveId +"号-直播间人数：" + map.size());

        Set<Map.Entry<String, Channel>> entrySet = map.entrySet();
        System.out.println("entrySet Size : "+entrySet.size());

        for (Map.Entry<String, Channel> member : entrySet) {
            Channel channel = member.getValue();
            // 转发给除自己以为的人
            if (channel != null) {
                System.out.println("分发消息：" + msg.getMessage()+"给: "+member.getKey());
                // 当receiverChannel 不为空的时候，从ChannelGroup去查找对象的channel是否存在
                Channel findChannel = users.find(channel.id());
                if (findChannel != null) {
                    findChannel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)));
                }
            }

        }
    }


    /**
     * 当web 客户端连接后触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println("客户端连接:" + channel.id().asLongText());
        users.add(channel);
    }

    /**
     * 当手动清理到客户端后台进程时，会触发该方法
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        // 当触发handlerRemoved，channelGroup会自动移除channel
        System.out.println("移除客户端连接：" + ctx.channel().id().asLongText());
        users.remove(ctx.channel());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // 发生异常之后关闭连接，随后从channelGroup中移除
        ctx.close();
        users.remove(ctx.channel());
    }
}
