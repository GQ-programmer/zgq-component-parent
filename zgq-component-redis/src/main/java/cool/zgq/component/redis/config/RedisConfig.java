package cool.zgq.component.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import cool.zgq.component.redis.properties.SerializerEnum;
import cool.zgq.component.redis.properties.TempConfigProperties;
import cool.zgq.component.redis.util.RedisUtils;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

import javax.annotation.Resource;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: redis配置类
 */
@Configuration
@Import({RedisUtils.class, TempConfigProperties.class})
public class RedisConfig {

    @Resource
    TempConfigProperties templateProperties;

    /**
     * generator key generator.
     *
     * @return the key generator
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };

    }

    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<Object, Object>();

        template.setConnectionFactory(factory);

        // key采用String的序列化方式
        template.setKeySerializer(initSerializer(templateProperties.getKeySerializer()));

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(initSerializer(templateProperties.getHashKeySerializer()));

        // value序列化方式采用jackson

        template.setValueSerializer(initSerializer(templateProperties.getValueSerializer()));

        // hash的value序列化方式采用jackson

        template.setHashValueSerializer(initSerializer(templateProperties.getHashValueSerializer()));

        template.afterPropertiesSet();

        return template;
    }

    private RedisSerializer initSerializer(SerializerEnum serializerEnum) {
        if(serializerEnum  == null) {
            //默认jdk
            return new JdkSerializationRedisSerializer();
        }
        Class clazz = serializerEnum.getSerializer();
        try {
            if(clazz.equals(Jackson2JsonRedisSerializer.class)) {
                // Jackson2JsonRedisSerializer 无无参构造
                Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);
                ObjectMapper om = new ObjectMapper();
                om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
                om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
                serializer.setObjectMapper(om);
                return serializer;
            }else {
                Object obj =  clazz.newInstance();
                RedisSerializer serializer = (RedisSerializer) obj;
                return serializer;
            }
        } catch (Exception e) {
            return new JdkSerializationRedisSerializer();
        }
    }

}
