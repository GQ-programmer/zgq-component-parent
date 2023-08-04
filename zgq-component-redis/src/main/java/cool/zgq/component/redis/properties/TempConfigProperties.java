package cool.zgq.component.redis.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: GQ
 * @Date: 2023/8/3 11:43
 * @Description: redis配置类
 */
@Configuration
@ConfigurationProperties(prefix = "zgq.redis.template")
public class TempConfigProperties {
    /**
     * 对于普通K-V操作时，key采取的序列化策略
     */
    private SerializerEnum keySerializer = SerializerEnum.STRING;
    /**
     * 在hash数据结构中，hash-key的序列化策略
     */
    private SerializerEnum hashKeySerializer = SerializerEnum.STRING;
    /**
     * value采取的序列化策略
     */
    private SerializerEnum valueSerializer = SerializerEnum.JDK;
    /**
     * hash-value的序列化策略
     */
    private SerializerEnum hashValueSerializer = SerializerEnum.JDK;

    public SerializerEnum getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(SerializerEnum keySerializer) {
        this.keySerializer = keySerializer;
    }

    public SerializerEnum getHashKeySerializer() {
        return hashKeySerializer;
    }

    public void setHashKeySerializer(SerializerEnum hashKeySerializer) {
        this.hashKeySerializer = hashKeySerializer;
    }

    public SerializerEnum getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(SerializerEnum valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public SerializerEnum getHashValueSerializer() {
        return hashValueSerializer;
    }

    public void setHashValueSerializer(SerializerEnum hashValueSerializer) {
        this.hashValueSerializer = hashValueSerializer;
    }
}
