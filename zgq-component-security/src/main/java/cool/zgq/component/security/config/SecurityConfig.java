package cool.zgq.component.security.config;

import cool.zgq.component.security.filter.LoginAuthenticationFilter;
import cool.zgq.component.security.filter.TokenFilter;
import cool.zgq.component.security.handler.LoginFailHandler;
import cool.zgq.component.security.handler.LoginSuccessHandler;
import cool.zgq.component.security.handler.LogoutSuccessHandler;
import cool.zgq.component.security.handler.RestAuthenticationEntryPoint;
import cool.zgq.component.security.properties.SecurityProperties;
import cool.zgq.component.security.properties.TokenProperties;
import cool.zgq.component.security.service.TokenService;
import cool.zgq.component.security.util.WebRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/04 10:23
 * @Description  security 5.7.1 后的版本 弃用了 extends WebSecurityConfigurerAdapter 用法
 *
 */
@Slf4j
@Configuration
@Import({TokenConfig.class, SecurityProperties.class, WebRequestUtil.class})
public class SecurityConfig {

    @Resource
    private TokenService tokenService;

    @Resource
    private TokenProperties tokenProperties;

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private AuthenticationConfiguration authenticationConfiguration;

    @Bean
    @SuppressWarnings("all")
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // 关闭session csrf
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();

        http
                // 登录认证
                .addFilterAt(getLoginAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // token认证
                .addFilterBefore(getTokenFilter(), LoginAuthenticationFilter.class)
                // login
                .formLogin().permitAll()
                // 登出
                .and().logout().logoutSuccessHandler(new LogoutSuccessHandler(tokenService));

        // UnPermitUrls 不为空
        if (securityProperties.getUnPermitUrls() != null && this.securityProperties.getUnPermitUrls().length != 0) {
             http.authorizeRequests()
                     // 只对 UnPermitUrls认证
                    .antMatchers(this.securityProperties.getUnPermitUrls()).authenticated()
                     // 其他放行
                    .anyRequest().permitAll();
        } else {
            http.authorizeRequests()
                    // 对PermitUrl放行
                    .antMatchers(this.securityProperties.getPermitUrls()).permitAll()
                    // 其他需认证
                    .anyRequest().authenticated();
        }
        // 异常处理器
        http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());

        return http.build();
    }

    /**
     * 获取token认证过滤器
     * @return
     */
    private TokenFilter getTokenFilter() {
        return new TokenFilter(tokenService, tokenProperties);
    }

    /**
     * 获取登陆认证过滤器
     * @return
     */
    private LoginAuthenticationFilter getLoginAuthenticationFilter() {
        LoginAuthenticationFilter authenticationFilter = new LoginAuthenticationFilter(securityProperties);
        try {
            authenticationFilter.setAuthenticationManager(authenticationConfiguration.getAuthenticationManager());
        }catch (Exception e) {
            log.error("AuthenticationManager获取错误" + e.getMessage());
        }
        authenticationFilter.setAuthenticationSuccessHandler(new LoginSuccessHandler(tokenService));
        authenticationFilter.setAuthenticationFailureHandler(new LoginFailHandler());
        return authenticationFilter;
    }



}
