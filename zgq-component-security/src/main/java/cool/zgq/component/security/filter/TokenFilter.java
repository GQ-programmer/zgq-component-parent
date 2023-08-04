package cool.zgq.component.security.filter;

import cool.zgq.component.common.threadLocal.ContextKey;
import cool.zgq.component.common.threadLocal.ThreadLocalContext;
import cool.zgq.component.security.entity.BaseLoginUser;
import cool.zgq.component.security.entity.LoginAuthentication;
import cool.zgq.component.security.properties.TokenProperties;
import cool.zgq.component.security.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: GQ
 * @Date: 2023/8/4 13:54
 * @Description: token 认证
 */
public class TokenFilter extends OncePerRequestFilter {


    private TokenService tokenService;

    private TokenProperties properties;


    public TokenFilter(TokenService tokenService, TokenProperties properties) {
        this.tokenService = tokenService;
        this.properties = properties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String token = request.getHeader(properties.getHeadKey());

            if (StringUtils.isBlank(token)) {
                throw new BadCredentialsException("未登录");
            }
            // 校验token
            BaseLoginUser user = (BaseLoginUser) tokenService.validateToken(token);
            if (user == null) {
                throw new BadCredentialsException("登录失效");
            }

            // 用户信息存入 ThreadLocal
            LoginAuthentication authentication = new LoginAuthentication(user, token);

            if (StringUtils.isNotBlank(user.getUserId())) {
                ThreadLocalContext.putContext(ContextKey.userId, user.getUserId());
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);

        } finally {
            // 过滤器链 返回执行时清除 用户信息
            ThreadLocalContext.removeContext();
        }

    }


}
