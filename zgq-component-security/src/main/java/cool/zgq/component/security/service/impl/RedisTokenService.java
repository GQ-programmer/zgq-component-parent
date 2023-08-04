package cool.zgq.component.security.service.impl;

import cool.zgq.component.redis.util.RedisUtils;
import cool.zgq.component.security.properties.TokenProperties;
import cool.zgq.component.security.service.TokenService;

import java.util.UUID;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: redis存放token方式
 */
public class RedisTokenService implements TokenService {


    private String tableName;

    private Long expire;

    public RedisTokenService(TokenProperties properties) {
        this.expire = properties.getExpire();

        this.tableName = properties.getRedis().getTableName();
    }

    @Override
    public String allocateToken(Object detail) {

        String token = createToken();

        //存入redis
        RedisUtils.set(tableName + ":" + token, detail, expire);

        return token;
    }

    @Override
    public Object validateToken(String token) {
        Object detail = RedisUtils.get(tableName + ":" + token);
        //校验时刷新一次token的有效时间
        RedisUtils.set(tableName + ":" + token, detail, expire);
        return detail;
    }


    @Override
    public Boolean updateToken(String token, Object detail) {
        return RedisUtils.set(tableName + ":" + token, detail, expire);
    }

    @Override
    public Boolean removeToken(String token) {
        return RedisUtils.delete(tableName + ":" + token);
    }

    /**
     * 随机字符串
     *
     * @return
     */
    private String createToken() {
        //创建token
        String token = UUID.randomUUID().toString().replaceAll("-", "") + "";
        return token;
    }

}
