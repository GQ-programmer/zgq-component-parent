package cool.zgq.component.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: redis配置类
 */
@Component
public class RedisUtils {

    protected static RedisUtils redisUtils;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param time 时间秒
     * @return
     */
    public static boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisUtils.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据 key获取过期时间
     *
     * @param key
     * @return
     */
    public static long getExpire(String key) {
        return redisUtils.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key 是否存在
     *
     * @param key
     * @return
     */
    public static boolean hasKey(String key) {
        try {
            return redisUtils.redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key
     */
    public static Boolean delete(String key) {
        return redisUtils.redisTemplate.delete(key);
    }

    /**
     * @param key
     * @return
     */
    public static Object get(String key) {
        return key == null ? null : redisUtils.redisTemplate.opsForValue().get(key);
    }

    /***     String: string是redis最基本的类型，一个key对应一个value*/

    /**
     * 普通类型缓存
     *
     * @param key
     * @param value
     * @return true 成功  false 失败
     */
    public static boolean set(String key, Object value) {
        return set(key, value, 0L);
    }

    /**
     * 普通类型缓存 增加失效时间
     *
     * @param key
     * @param value
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true 成功  false 失败
     */
    public static boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisUtils.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                redisUtils.redisTemplate.opsForValue().set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // ============================hash=============================
    /**
     * @param key
     * @param item
     * @return
     */
    public static Object hGet(String key, Object item) {
        return redisUtils.redisTemplate.opsForHash().get(key, item);
    }

    /***  Hash: 我们可以将Redis中的Hash类型看成具有<key,<key1,value>>,其中同一个key可以有多个不同key值的<key1,value>，所以该类型非常适合于存储值对象的信息*/

    /**
     * 获取 整个对象
     *
     * @param key
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hGetAll(String key) {
        return redisUtils.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 插入hash 某个项目的 value
     *
     * @param key
     * @return 成功
     */
    public static boolean hSet(String key, Object item, Object value) {
        return hSet(key, item, value, 0L);
    }

    /**
     * 插入hash 某个项目的 value
     *
     * @param key
     * @param item
     * @param value
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return
     */
    public static boolean hSet(String key, Object item, Object value, long time) {
        try {
            redisUtils.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 插入 整个对象
     *
     * @param key
     * @return 对应的多个键值
     */
    public static boolean hSetAll(String key, Map<Object, Object> map) {
        return hSetAll(key, map, 0L);
    }

    /**
     * 获取 整个对象
     *
     * @param key
     * @return 对应的多个键值
     */
    public static boolean hSetAll(String key, Map<Object, Object> map, long time) {

        try {
            redisUtils.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static long hRemove(String key, Object... values) {
        try {
            Long count = redisUtils.redisTemplate.opsForHash().delete(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ============================set=============================
    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisUtils.redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object... values) {
        return sSetAndTime(key, 0L, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = redisUtils.redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            return redisUtils.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key) {
        try {
            return redisUtils.redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long sRemove(String key, Object... values) {
        try {
            Long count = redisUtils.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    // ============================list=============================
    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisUtils.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key) {
        try {
            return redisUtils.redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisUtils.redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSet(String key, Object value) {
        return lSet(key, value, 0L);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisUtils.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @return
     */
    public static boolean lSetAll(String key, List<Object> value) {
        return lSetAll(key, value, 0L);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     * @return
     */
    public static boolean lSetAll(String key, List<Object> value, long time) {
        try {
            redisUtils.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index, Object value) {
        try {
            redisUtils.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key, long count, Object value) {
        try {
            Long remove = redisUtils.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostConstruct
    public void init() {
        redisUtils = this;
        redisUtils.redisTemplate = this.redisTemplate;
    }
}