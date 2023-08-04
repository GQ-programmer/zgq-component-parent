package cool.zgq.component.security.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:44
 * @Description
 */
@Data
@Configuration
@ConfigurationProperties("zgq.security")
public class SecurityProperties {

    /**
     * 不需要认证，允许通过的的url,其余都认证
     */
    private String[] permitUrls = {
            "/swagger-ui.html", "/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs",
            "/file/upload/**"
    };

    /**
     * 需要认证的url  如果 设置了。则 只对 unPermitUrls 进行认证。
     */
    private String[] unPermitUrls ={};

    private String loginUrl = "/login";

    private String loginMethod = "POST";

    private String principalKey = "username";

    private String credentialSKey = "password";
}
