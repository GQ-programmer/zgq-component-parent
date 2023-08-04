package cool.zgq.component.common.util;


import cool.zgq.component.common.threadLocal.ContextKey;
import cool.zgq.component.common.threadLocal.ThreadLocalContext;

/**
 * @Author: GQ
 * @Date: 2023/7/24
 * @Description: 用户信息
 */
public abstract class UserUtils {

    /**
     * 线程放入用户id
     *
     * @return
     */
    public static void setUserId(Long userId) {
        ThreadLocalContext.putContext(ContextKey.userId, userId);
    }

    /**
     * 线程获取用户id
     *
     * @return
     */
    public static Long getUserId() {
        Object res = ThreadLocalContext.getContextValue(ContextKey.userId);

        if(res !=null){
            return Long.parseLong(res.toString());
        }else  {
           return null;
        }
    }

    /**
     * 线程获取用户id
     *
     * @return
     */
    @Deprecated
    public static String getCurrentUserId() {
        Object res = ThreadLocalContext.getContextValue(ContextKey.userId);

        return res == null ? null : res.toString();
    }

    /**
     * 线程放入角色id
     *
     * @return
     */
    public static void setRoleId(Long roleId) {
        ThreadLocalContext.putContext(ContextKey.roleId, roleId);
    }

    /**
     * 线程获取角色id
     *
     * @return
     */
    public static Long gerRoleId() {
        Object res = ThreadLocalContext.getContextValue(ContextKey.roleId);

        if(res !=null){
            return Long.parseLong(res.toString());
        }else  {
            return null;
        }
    }

}
