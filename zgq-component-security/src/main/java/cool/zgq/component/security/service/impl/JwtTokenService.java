package cool.zgq.component.security.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.security.properties.TokenProperties;
import cool.zgq.component.security.service.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: JWT存放token方式
 */
public class JwtTokenService implements TokenService {


    private Long expire = 1L;

    private String secret = "ZGQ";

    private String issuer = "issuer";


    private static String claimsKey = "detail";

    public JwtTokenService(TokenProperties properties) {
        this.expire = properties.getExpire();
        this.secret = properties.getJwt().getSecret();
        this.issuer = properties.getJwt().getIssuer();
    }

    @Override
    public String allocateToken(Object detail) {
        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(secret);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
//        Map <String, Object> claims = new HashMap <String, Object>();
//        claims.put(claimsKey, detail);

        Claims claims  = new DefaultClaims();
        claims.put(claimsKey,detail);
        claims.put("class",detail.getClass());

        // Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setIssuer(issuer)
                .setClaims(claims)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (expire >= 0) {
            long expMillis = nowMillis + expire * 1000;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    @Override
    public Object validateToken(String token) {

        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(secret))

                .parseClaimsJws(token).getBody();
        if (claims == null || claims.getExpiration().before(new Date())) {
            return null;
        }

        Object obj =  claims.get(claimsKey);
        String clazzStr =  claims.get("class",String.class);


        try {
            Class clazz = Class.forName(clazzStr);

            if(obj instanceof  Map) {
                return JSON.toJavaObject((JSONObject)JSON.toJSON(obj),clazz);
            }else {
                return obj;
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return obj;
    }


    @Override
    public Boolean updateToken(String token, Object detail) {
        throw new UnsupportedOperationException("jwt token 不支持更新用户信息");
    }

    @Override
    public Boolean removeToken(String token) {
        throw new UnsupportedOperationException("jwt token 不支持移除失效");
    }
}
