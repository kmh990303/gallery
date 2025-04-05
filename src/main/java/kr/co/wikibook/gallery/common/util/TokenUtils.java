package kr.co.wikibook.gallery.common.util;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    private static final Key signKey;

    static {
        String secretKey = "SECURITY_KEY_2023042319572107_!!";
        byte[] secretKeyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        signKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // 토큰 발급
    public static String generate(String subject, String name, Object value, int expMinutes) {

        Date expTime = new Date();

        expTime.setTime(expTime.getTime() + 1000L * 60 * expMinutes);

        HashMap<String, Object> headerMap = new HashMap<>();
        headerMap.put("typ", "JWT");
        headerMap.put("alg", "HS256");

        HashMap<String, Object> claims = new HashMap<>();
        claims.put(name, value);

        JwtBuilder builder = Jwts.builder()
                .setHeader(headerMap)
                .setSubject(subject)
                .setExpiration(expTime)
                .addClaims(claims)
                .signWith(signKey, SignatureAlgorithm.HS256);

        return builder.compact();
    }

    public static boolean isValid(String token) {
        if (StringUtils.hasLength(token)) {
            try {
                Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token);
                return true;
            } catch (ExpiredJwtException e) {

            } catch (JwtException e) {

            }
        }
        return false;
    }

    public static Map<String, Object>getBody(String token) {
        return Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
    }
}
