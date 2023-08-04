package cool.zgq.component.security.handler;

import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.common.common.BaseResponse;
import cool.zgq.component.common.common.ErrorCode;
import cool.zgq.component.common.common.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: DaleShay
 * @Date: 2023/08/04 11:07
 * @Description: 登录失败处理器
 */
public class LoginFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.OK.value());
        BaseResponse baseResponse = ResultUtils.error(ErrorCode.FAIL_INVALID_USER, exception.getMessage());
        response.getWriter().write(JSONObject.toJSONString(baseResponse));
    }
}