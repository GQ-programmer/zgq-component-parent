package cool.zgq.component.security.handler;

import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.common.common.ErrorCode;
import cool.zgq.component.common.common.ResultUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/04 10:23
 * @Description  异常处理器
 *
 */
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter()
                .write(JSONObject.toJSONString(ResultUtils.error(ErrorCode.FAIL_INVALID_USER,"身份认证失败")));
    }
}