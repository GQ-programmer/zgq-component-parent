package cool.zgq.component.security.util;

import cool.zgq.component.security.entity.LoginAuthentication;
import cool.zgq.component.security.entity.BaseLoginUser;
import cool.zgq.component.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/03 11:47
 * @Description
 */
@Component
public class WebRequestUtil {

    protected static WebRequestUtil webRequestUtil;

    @Autowired
    private TokenService tokenService;


    @PostConstruct
    public void init() {
        webRequestUtil = this;
        webRequestUtil.tokenService = this.tokenService;
    }

    /**
     * 获取登录用户
     *
     * @return
     */
    public static BaseLoginUser getLoginUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object user = authentication.getDetails();
        if (user == null || !(user instanceof BaseLoginUser)) {
            return null;
        }

        return (BaseLoginUser) user;
    }

    public static void updateLoginUser(BaseLoginUser vo) {
        LoginAuthentication authentication = (LoginAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return;
        }
        String token = authentication.getToken();
        BaseLoginUser user = (BaseLoginUser) authentication.getDetails();
        if (user == null || !(user instanceof BaseLoginUser)) {
            return;
        }

        authentication.setDetails(vo);
        webRequestUtil.tokenService.updateToken(token, vo);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 获取用户Id
     *
     * @return
     */
    public static Long getUserId() {

        BaseLoginUser user = getLoginUser();

        if (user != null) {
            return Long.parseLong(user.getUserId());
        }
        return null;
    }

}
