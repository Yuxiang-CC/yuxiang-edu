package com.yuxiang.edu.common.util;

import cn.hutool.core.date.DateUtil;
import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: yuxiang
 * @Date: 2020/11/15 15:47
 */

public class JWTUtils {

    // 2020/11/15 16:17 生成的Token 过期时间为 1小时
    // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI5ZjY3OTFmMi1mZmJkLTQ0NDAtYWZhNy04NTQ2YTc0N2RjOTciLCJzdWIiOiJ5dXhpYW5nLWVkdSIsImlhdCI6MTYwNTc0NjcwNywiZXhwIjoxNjA1OTc3MTA3LCJpZCI6IjIwMTg0MTQxMDkwIiwibmlja05hbWUiOiLku7vph5HljZoiLCJhdmF0YXIiOiIifQ.pu59KylpGccESwQfgfr9pyENuYo9RYGCzrUqhhzMk1s
    /**
     * 经过 cn.hutool.core.codec.Base64.encode(“yuxiangdesan”); 加密
     */
    public static final String APP_SECRET_KEY = "eXV4aWFuZ2Rlc2Fu";
    // 令牌有效时间 （单位：小时）
    public static final Integer EXPIRATION_DATE = 1 << 6;
    // 请求头
    public static final String REQUEST_HEADER = "Yuxiang-edu-token";

    public static void main(String[] args) {

        /*String a = "yuxiangdesan";

        // 17vKU8W4JMG8dQF8lk9VNnkdMOeWn4rJMva6F0XsLrrT53iKBnqo
        String encode = Base64.encode(a);
        System.out.println(encode);

        // 还原为a
        String decodeStr = Base64.decodeStr(encode);
        System.out.println(decodeStr);*/

        String token = JWTUtils.genJwt("1334103155167727618", "周杰伦", "https://yuxiang-edu.oss-cn-qingdao.aliyuncs.com/teacher/2020/12/02/09999d553e9041eea66d9e80b4b1d8f9.jpg");
        System.out.println(token);
//
//        boolean s = JWTUtils.checkJWT("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJjZGJmMGNiMi1jNzhlLTQ2NzYtYjkxMC1mMWRmMmMyODY1MDQiLCJzdWIiOiJ5dXhpYW5nLWVkdSIsImlhdCI6MTYwNjE4NzQxMCwiZXhwIjoxNjA2NDE3ODEwLCJpZCI6IjEzMzA4NzI5NzAyODUwMTUwNDIiLCJuaWNrTmFtZSI6InZpcF8xcnoydDlyeGd0IiwiYXZhdGFyIjoiaHR0cHM6Ly95dXhpYW5nLWVkdS5vc3MtY24tcWluZ2Rhby5hbGl5dW5jcy5jb20vdWNlbnRlci8yMDIwLzExLzE3L2NjOWY4YjljY2UwMDRjNGE5ZWQ2ZGY1M2RiOWRiZDBhLnBuZyJ9.sH7-_QnLJUVxw1rEDldSs66TQpNBPyjs7C-cwdUkrDE");
//        System.out.println(s);


//        JwtParser parser = Jwts.parser();
//        parser.setSigningKey(JWTUtils.APP_SECRET_KEY);
//        Claims claims = parser.parseClaimsJws("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJqdGkiOiJmMTE5N2ZlNy1kMjhmLTQ0YTMtOTQzMy1jMTU0NzhlNjgwOGIiLCJzdWIiOiJ5dXhpYW5nLWVkdSIsImlhdCI6MTYwNTUwNTI0MiwiZXhwIjoxNjA1NTA4ODQyLCJpZCI6IjIwMTg0MTQxMDkwIiwibmlja05hbWUiOiLku7vph5HljZoiLCJhdmF0YXIiOiJodHRwczovL3d3dy5iYWlkdS5jb20ifQ.TlyFICgINFr_KENNuPGf8jesdk_-ZPpmzkvbMa9-Bww")
//        .getBody();
//
//        JwtInfo jwtInfo = new JwtInfo(claims.get("id").toString(), claims.get("nickName").toString(), claims.get("avatar").toString());
//
//        System.out.println(jwtInfo);
//        String originalFilename = "fasdffsd.png";
//        String file = originalFilename.substring(originalFilename.lastIndexOf("."));
//        System.out.println(IdUtil.fastSimpleUUID() + file);

    }

    public static String genJwt(String id, String nickName, String avatar) {

        JwtBuilder builder = Jwts.builder();

        // 第一部分： JWT头 header
        builder.setHeaderParam("alg", "HS256"); // 签名算法
        builder.setHeaderParam("typ", "JWT"); // 令牌类型

        // 第二部分： JWT有效载荷 playod
        // 默认字段 7个
        builder.setId(UUID.randomUUID().toString()); // 唯一身份标识
        builder.setSubject("yuxiang-edu"); // 令牌主题
        Date date = new Date();
        builder.setIssuedAt(date); // 令牌签发时间
        builder.setExpiration(DateUtil.offsetHour(date, EXPIRATION_DATE)); // 令牌过期时间
        // 定义私有字段 claim()方法
        builder.claim("id", id);
        builder.claim("nickName", nickName);
        builder.claim("avatar", avatar);

        // 第三部分： 签名哈希
        builder.signWith(SignatureAlgorithm.HS256, JWTUtils.APP_SECRET_KEY);

        // 将三部分连接起来
        String jwtCompact = builder.compact();

        return jwtCompact;
    }

    public static boolean checkJWT(String jwtToken) {

        if (StringUtils.isEmpty(jwtToken)) {
            return false;
        }

        try {
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(JWTUtils.APP_SECRET_KEY);
            parser.parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean checkJWT(HttpServletRequest request) {

        try {
            String jwtToken = request.getHeader(REQUEST_HEADER);
            if (StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            JwtParser parser = Jwts.parser();
            parser.setSigningKey(JWTUtils.APP_SECRET_KEY);
            parser.parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static JwtInfo getInfoFromJWT(HttpServletRequest request) {

        String jwtToken = request.getHeader(REQUEST_HEADER);
        if (StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        JwtParser parser = Jwts.parser();
        parser.setSigningKey(JWTUtils.APP_SECRET_KEY);
        Claims claims = parser.parseClaimsJws(jwtToken).getBody();

        JwtInfo jwtInfo = new JwtInfo(claims.get("id").toString(), claims.get("nickName").toString(), claims.get("avatar").toString());
        return jwtInfo;
    }

}
