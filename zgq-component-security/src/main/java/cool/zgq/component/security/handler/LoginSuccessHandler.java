package cool.zgq.component.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.common.common.ResultUtils;
import cool.zgq.component.security.entity.LoginAuthentication;
import cool.zgq.component.security.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @Author: GQ
 * @Date: 2023 08/04 11:03
 * @Description: 登录成功处理器
 */
@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {


    private TokenService tokenService;

    public LoginSuccessHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object userInfo = authentication.getDetails();
        String token =  ( (LoginAuthentication) authentication).getToken();

        JSONObject result = (JSONObject) JSON.toJSON(userInfo);
        result.put("token", token);


//        //最近一次登录时间
//        loginSuccessHandler.userService.updateLoginTime(Long.parseLong(userInfo.getUserId()),new Date());

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(JSON.toJSONString(ResultUtils.success(result)));
    }
}
