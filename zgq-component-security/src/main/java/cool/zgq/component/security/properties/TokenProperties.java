package cool.zgq.component.security.properties;

import cool.zgq.component.security.enums.TokenMode;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/03 12:14
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zgq.security.token")
public class TokenProperties {

    /**
     * token header key
     */
    private String headKey ="Authentication";


    /****
     * 过期时间 （秒）
     * 默认2 小时
     */
    private Long expire = 60L * 60L * 2L;


    private TokenMode tokenMode = TokenMode.JWT;


    private JwtTokenProperties jwt = new JwtTokenProperties();


    private RedisTokenProperties redis = new RedisTokenProperties();


    @Data
    public static class JwtTokenProperties {

        private String secret = "ZGQ";

        private String issuer = "ISSUER";

    }

    @Data
    public static class RedisTokenProperties {

        /**
         * redis 表名
         */
        private String tableName = "ZGQ_TOKEN";


    }
}
