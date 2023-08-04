package cool.zgq.component.security.provider;

import cool.zgq.component.security.entity.LoginAuthentication;
import cool.zgq.component.security.entity.BaseLoginUser;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.annotation.Resource;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/02 16:34
 * @Description 自定义认证提供者 抽象类
 */
public abstract class AbstractAuthenticationProvider implements AuthenticationProvider {

    @Resource
    //TokenService tokenService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return this.authenticate((LoginAuthentication) authentication);
    }

    public Authentication authentication(LoginAuthentication authentication) throws AuthenticationException {
        // 获取用户信息
        BaseLoginUser userDetails = this.retrieveUser(authentication);
        if (userDetails == null) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        // 创建认证信息
        return this.createLoginAuthentication(userDetails);
    }

    /**
     * 创建认证成功后的对象
     * @param userDetails
     * @return
     */
    protected Authentication createLoginAuthentication(BaseLoginUser userDetails){
        // 生成token
        String token = "";
        return new LoginAuthentication(userDetails, token);
    }

    /**
     * 获取用户接口 子类进行实现
     * @param authentication
     * @return
     */
    protected abstract BaseLoginUser retrieveUser(LoginAuthentication authentication);

    /**
     * 标识 该 provider 支持那种 认证对象
     *
     * 仅支持自定义 LoginAuthenticationren认证对象
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return LoginAuthentication.class.isAssignableFrom(authentication);
    }
}
