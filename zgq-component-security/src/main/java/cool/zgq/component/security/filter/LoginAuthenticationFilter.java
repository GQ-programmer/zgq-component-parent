package cool.zgq.component.security.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.security.properties.SecurityProperties;
import cool.zgq.component.security.entity.LoginAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:40
 * @Description 自定义认证逻辑
 */
public class LoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private String RESTFUL_PRINCIPAL_KEY = "username";

    private String RESTFUL_CREDENTIALS_KEY = "password";

    public LoginAuthenticationFilter(SecurityProperties properties) {
        super(new AntPathRequestMatcher(properties.getLoginUrl(), properties.getLoginMethod()));
        this.RESTFUL_PRINCIPAL_KEY = properties.getPrincipalKey();
        this.RESTFUL_CREDENTIALS_KEY = properties.getCredentialSKey();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Authentication authRequest;

        authRequest = createAuthenticationToken(request);
        // 认证管理器去寻找 -> 认证提供者 -> 进行认证返回 -> 认证后的Authentication对象
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    /**
     * 生成一个未认证的 Authentication
     * @param request
     * @return
     */
    private Authentication createAuthenticationToken(HttpServletRequest request) {
        JSONObject body = readRequestBody(request);
        String principal = body.getString(RESTFUL_PRINCIPAL_KEY);
        String credentials = body.getString(RESTFUL_CREDENTIALS_KEY);
        principal = principal.trim();
        return new LoginAuthentication(principal,credentials,body);
    }

    /**
     * 获取请求体参数
     * @param request
     * @return
     */
    private JSONObject readRequestBody(HttpServletRequest request) {
        StringBuilder requestBody = new StringBuilder();
        BufferedReader reader = null;

        try {
            // 获取请求输入流
            reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return JSON.parseObject(requestBody.toString());
    }
}
