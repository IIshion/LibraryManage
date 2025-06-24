package sys.librarymanage.Utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.util.Date;
import java.util.Map;

public class JwtUtils {
    // 密钥
    private static final String KEY = "nina";

    // 生成token
    public static String generateToken(Map<String, Object> claims, String username) {
        return JWT.create()
                .withClaim("claims", claims)
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))  // 过期时间12小时
                .withIssuedAt(new Date()) // 签发时间
                .sign(Algorithm.HMAC256((KEY)));
    }

    // 接受并且验证token，返回业务数据
    public static Map<String, Object> parseToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(KEY))
                    .build()
                    .verify(token)
                    .getClaim("claims")
                    .asMap();
        } catch (JWTVerificationException e) {
            throw new RuntimeException(e);
        }
    }

    // 从token中提取用户名
    public static String getUsername(String token){
        Map<String, Object> claims  = parseToken(token);
        return claims.get("username").toString();
    }

    // 主动检查token是否过气
    public static boolean isTokenExpired(String token) {
        try {
            Date expiresAt = JWT.require(Algorithm.HMAC256(KEY))
                    .build()
                    .verify(token)
                    .getExpiresAt();
            return expiresAt.before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

}