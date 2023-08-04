package cool.zgq.component.redis.properties;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.OxmSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: redis配置类
 */
public enum  SerializerEnum {
    /**
     * jdk序列化
     */
    JDK(JdkSerializationRedisSerializer.class),
    /**
     * string 对象不适用
     */
    STRING(StringRedisSerializer.class),

    /**
     * xml 格式储存
     */
    XML(OxmSerializer.class),

    /**
     * spring  redis自带json
     */
    JSON(Jackson2JsonRedisSerializer.class),

    /**
     * 阿里巴巴 fast json
     */
    FAST_JSON(GenericFastJsonRedisSerializer.class);



    private Class serializer;

    SerializerEnum(Class serializer) {
        this.serializer = serializer;
    }


    public Class getSerializer() {
        return serializer;
    }

}
