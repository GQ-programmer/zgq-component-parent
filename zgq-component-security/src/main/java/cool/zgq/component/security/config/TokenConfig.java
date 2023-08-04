package cool.zgq.component.security.config;

import cool.zgq.component.security.enums.TokenMode;
import cool.zgq.component.security.properties.TokenProperties;
import cool.zgq.component.security.service.TokenService;
import cool.zgq.component.security.service.impl.JwtTokenService;
import cool.zgq.component.security.service.impl.RedisTokenService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/04 16:56
 * @Description
 */
@Configuration
@Import({TokenProperties.class})
public class TokenConfig {

    @Resource
    TokenProperties properties;


    @Bean
    @ConditionalOnMissingBean
    public TokenService tokenService() {

        if(properties.getTokenMode().equals(TokenMode.JWT)){
            return new JwtTokenService(properties);
        }else if(properties.getTokenMode().equals(TokenMode.REDIS)){
            return new RedisTokenService(properties);
        }
        return null;
    }
}
