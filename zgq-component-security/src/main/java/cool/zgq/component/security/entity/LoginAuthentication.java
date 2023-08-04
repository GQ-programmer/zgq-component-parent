package cool.zgq.component.security.entity;

import com.alibaba.fastjson.JSONObject;
import cool.zgq.component.security.entity.BaseLoginUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:37
 * @Description
 */
public class LoginAuthentication extends AbstractAuthenticationToken {

    /**
     * 标识用户身份
     */
    private String principal;

    /**
     * 凭证
     */
    private String credentials;

    /**
     * 认证成功后填入
     */
    private String token;

    /**
     * 用户信息 认证成功后填入
     */
    private BaseLoginUser userDeatils;

    /**
     * 自定义参数
     */
    private JSONObject param;

    /**
     * 创建认证成功后 对象
     * @param userDeatils
     * @param token
     */
    public LoginAuthentication(BaseLoginUser userDeatils, String token){
        super(null);
        this.userDeatils = userDeatils;
        this.principal = userDeatils.getUsername();
        this.token = token;
        super.setAuthenticated(true);
    }

    /**
     * 创建认证前对象
     * @param principal
     * @param credentials
     * @param param
     */
    public LoginAuthentication(String principal, String credentials, JSONObject param) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.param = param;
        super.setAuthenticated(false);
    }

    /**
     * 设置认证状态
     * @param authenticated
     */
    @Override
    public void setAuthenticated(boolean authenticated) {
        super.setAuthenticated(authenticated);
    }

    public JSONObject getParam(){
        return this.param;
    }

    public String getToken() {
        return token;
    }

    public BaseLoginUser getUserDeatils() {
        return userDeatils;
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}
