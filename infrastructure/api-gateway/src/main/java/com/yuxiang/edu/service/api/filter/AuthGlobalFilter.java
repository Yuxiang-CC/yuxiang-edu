package com.yuxiang.edu.service.api.filter;

import com.google.gson.JsonObject;
import com.yuxiang.edu.common.result.ResultCodeEnum;
import com.yuxiang.edu.common.util.JWTUtils;
import com.yuxiang.edu.service.api.constant.ApiGatewayConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 全局过滤器
 * @Author: yuxiang
 * @Date: 2020/11/23 16:19
 */
@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // TODO 是否设置为静态
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        // 判断路径中 存在 /api/**/auth/** 的需要鉴权
        URI uri = request.getURI();
        String path = uri.getPath();
        System.out.println("path:" + path);
        // 判断该路径是否需要鉴权
        if (antPathMatcher.match(ApiGatewayConstant.WEB_AUTH_URL, path)) {
            // 获取token
            List<String> token = request.getHeaders().get(ApiGatewayConstant.TOKEN_NAME);

            // 没有令牌
            if (token == null) {
                return out(response, ResultCodeEnum.TOKEN_AUTHENTICATION);
            }
            boolean isCheck = JWTUtils.checkJWT(token.get(0));
            // 令牌校验失败
            if (!isCheck) {
                return out(response, ResultCodeEnum.TOKEN_EXPIRED);
            }
        } else if (antPathMatcher.match(ApiGatewayConstant.ADMIN_URL, path)) {
            // TODO 判断是否有管理员权限
        }

        return chain.filter(exchange);
    }

    private Mono<Void> out(ServerHttpResponse response, ResultCodeEnum codeEnum) {

        JsonObject message = new JsonObject();
        message.addProperty("success", codeEnum.getSuccess());
        message.addProperty("code", codeEnum.getCode());
        message.addProperty("data", "gateway auth error");
        message.addProperty("message", codeEnum.getMessage());
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBufferFactory dataBufferFactory = response.bufferFactory();
        DataBuffer buffer = dataBufferFactory.wrap(bytes);

        // 指定编码，否则在浏览器中会中文乱码
        response.getHeaders().add("Content-Type", "application/json;charset=utf-8");

        // 输出http响应
        return response.writeWith(Mono.just(buffer));
    }

    /**
     *  优先级，数越小优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }


}
