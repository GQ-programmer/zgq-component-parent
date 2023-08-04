package cool.zgq.component.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description Swagger自定义属性类
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "zgq.swagger")
public class SwaggerProperties {

    /**
     * 是否启用Swagger
     */
    private boolean enable = true;

    /**
     * 扫描的基本包
     */
    private String basePackage;

    /**
     * ApiInfo标题
     */
    private String title;

    /**
     * ApiInfo描述
     */
    private String description;

    /**
     * ApiInfo版权信息
     */
    private String license;

    /**
     * ApiInfo协议地址
     */
    private String serviceUrl;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人URL
     */
    private String contactUrl;

    /**
     * 联系人邮箱
     */
    private String contactEmail;
}
