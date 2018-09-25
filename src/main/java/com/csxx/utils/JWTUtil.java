package com.csxx.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import com.csxx.enums.common.JWTTokenVerifyEnum;
import org.springframework.beans.factory.annotation.Value;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

public class JWTUtil {

    @Value("${jwt-token}")
    private static String jwtToken;

    /**
     * 创建JWT-TOKEN
     * @param map claim信息map集合
     * @param limitTime 有效时长
     * @return 返回JWT-TOKEN字符串
     * @throws UnsupportedEncodingException
     */
    public static String createToken(Map<String, String> map, long limitTime) throws UnsupportedEncodingException {
            Date date = new Date(System.currentTimeMillis() + limitTime);
            Algorithm algorithm = Algorithm.HMAC256(jwtToken);
            JWTCreator.Builder builder = JWT.create();
            for (Map.Entry<String, String> entry: map.entrySet()) {
                builder.withClaim(entry.getKey(), entry.getValue());
            }
            return builder.withExpiresAt(date).sign(algorithm);
    }

    /**
     * 校验令牌是否正确
     * @param token 令牌
     * @param map claim集合
     * @return JWTToken校验枚举结果
     * @throws UnsupportedEncodingException
     */
    public static JWTTokenVerifyEnum verify(String token, Map<String, String> map) throws UnsupportedEncodingException {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtToken);
            Verification verification = JWT.require(algorithm);
            for (Map.Entry<String, String> entry: map.entrySet()) {
                verification.withClaim(entry.getKey(), entry.getValue());
            }
            JWTVerifier verifier = verification.build();
            verifier.verify(token);
            return JWTTokenVerifyEnum.VALID;
        } catch (AlgorithmMismatchException e) {
            return JWTTokenVerifyEnum.ALGORITHM_MISMATCH;
        } catch (SignatureVerificationException e) {
            return JWTTokenVerifyEnum.INVALID_SIGNATURE;
        } catch (TokenExpiredException e) {
            return JWTTokenVerifyEnum.TOKEN_EXPIRED;
        } catch (InvalidClaimException e) {
            return JWTTokenVerifyEnum.INVALID_CLAIM;
        }
    }

    /**
     * 获得token中的信息
     * @param token 令牌
     * @return 返回Claim集合
     */
    public static Map<String, Claim> getClaim(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
