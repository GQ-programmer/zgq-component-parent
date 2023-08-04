package cool.zgq.component.security.service;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/02 16:34
 * @Description token生成方式
 */
public interface TokenService {

    /**
     * 生成分配token
     *
     * @param detail 用户信息
     * @return
     */
    String allocateToken(Object detail);

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    Object validateToken(String token);


    /**
     * 更新 token 关联 detail 信息
     *
     * @param token
     * @param detail
     * @return
     */
    Boolean updateToken(String token, Object detail);


    /**
     * 移除token
     *
     * @param token
     * @return
     */
    Boolean removeToken(String token);
}
