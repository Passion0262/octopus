package com.example.octopus.utils;

import io.jsonwebtoken.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hao
 * @date: 2021/6/2 14:18
 * token和cookie的配置内容
 */

public class CookieTokenUtils {
    // token///////////////////////////////////////////////
    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";

    private static final String SECRET = "platformsecret";
    private static final String ISS = "echisan";

    // 过期时间是3600秒，既是1个小时
    private static final long EXPIRATION = 3600L;

    // 选择了记住我之后的过期时间为7天
    private static final long EXPIRATION_REMEMBER = 604800L;

    // 创建token
    public static String createToken(String id, String username, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setIssuer(ISS)
                .setId(id)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    // 从token中获取用户名
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    // 从token中获取用户id
    public static String getId(String token) {
        return getTokenBody(token).getId();
    }

    // token全部解密
    private static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // 检查token合法性
    public static TokenCheckUtils validateToken(HttpServletRequest request, String cookie) {
        TokenCheckUtils tokenCheck = new TokenCheckUtils();
        Claims claims = null;
        try {
            String token = getCookie(request, cookie).getValue();
            claims = getTokenBody(token);

            tokenCheck.setSuccess(true);
            tokenCheck.setUserName(getTokenBody(token).getSubject());
            tokenCheck.setUserNum(getTokenBody(token).getId());
            tokenCheck.setClaims(claims);
        } catch (ExpiredJwtException e) {  // token过期
            tokenCheck.setErrorType("Exception： token过期");
            tokenCheck.setException(e);
            tokenCheck.setSuccess(false);
        } catch (SignatureException e) {  // 认证错误
            tokenCheck.setErrorType("Exception： 签名错误");
            tokenCheck.setException(e);
            tokenCheck.setSuccess(false);
        } catch (Exception e) {  // 其他异常
            tokenCheck.setErrorType("Exception： 其他错误");
            tokenCheck.setException(e);
            tokenCheck.setSuccess(false);
        }
        return tokenCheck;
    }


    // cookie////////////////////////////////////////////
    // 设置cookie

    public static void setCookie(String number, String name, HttpServletResponse response, String cookieName) {
        // 生成token
        // boolean remember = rememberme == "1" ? true : false;
        String token = createToken(number, name,false);

        Cookie cookie = new Cookie(cookieName, token);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge((int)EXPIRATION);
        // 向客户端发送cookie
        response.addCookie(cookie);
        System.out.println("为用户"+number+":"+name+"生成了名为"+cookieName+"的cookie");
    }

    // 删除cookie
    public static void deleteCookie(HttpServletRequest request, String name){
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        //2.查找是否存在cookie,修改有效期为0
        if (cookieMap.containsKey(name)) {
            cookieMap.get(name).setMaxAge(0);
            cookieMap.get(name).setValue("");

            System.out.println("删除了名为"+name+"的cookie");
            System.out.println(cookieMap.get(name).getValue());
            System.out.println(cookieMap.get(name).getMaxAge());
        }
    }

    // 查找cookie
    public static Cookie getCookie(HttpServletRequest request, String name) throws NullPointerException{
        //1.将cookies放到map中去
        Map<String, Cookie> cookieMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        //2.查找是否存在cookie,是则返回查找到的cookie
        if (cookieMap.containsKey(name)) {
            return cookieMap.get(name);
        } else {
            return null;
        }
    }


    /**
     * 获取cookie中token里的用户号
     */
    public static String getCookieUserNum(HttpServletRequest request, String cookieName) {
        try{
            // 获取cookie中的token
            String token = getCookie(request, cookieName).getValue();
            String userNum = getId(token);
            return userNum;
        }catch (NullPointerException e){
            return "0";
        }
    }

    /**
     * 获取cookie中token里的用户名
     */
    public static String getCookieUserName(HttpServletRequest request, String cookieName) {
        // 获取cookie中的token
        String token = getCookie(request, cookieName).getValue();
        String userName = getUsername(token);
        return userName;
    }
}
