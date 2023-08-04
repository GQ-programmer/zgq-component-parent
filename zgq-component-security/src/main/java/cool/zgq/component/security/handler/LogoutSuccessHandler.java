package cool.zgq.component.security.handler;

import com.alibaba.fastjson.JSON;
import cool.zgq.component.common.common.ResultUtils;
import cool.zgq.component.security.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: GQ
 * @Date: 2023 08/04 11:03
 * @Description: 登出成功处理器
 */
public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    TokenService tokenService;


    public LogoutSuccessHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = request.getHeader("Authentication");
        if (StringUtils.isAnyBlank(token)) {
            tokenService.removeToken(token);
        }

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSON.toJSONString(ResultUtils.success(authentication)));
    }
}
