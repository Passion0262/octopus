package com.example.octopus.utils;

import io.jsonwebtoken.Claims;
import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/6/3 19:18
 * token验证结果
 */
@Data
public class TokenCheckUtils {

    private boolean success = false;  // token验证成功
    private String userName = null;  // 用户名
    private String userNum = null;  //用户编号
    private Claims claims;  // token解码
    private String errorType = "未经验证，请检查是否有token";  // token验证失败类型（过期、签名错误、其他等）
    private Exception exception;  // 异常代码

}
