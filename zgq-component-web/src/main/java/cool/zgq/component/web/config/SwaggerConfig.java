package cool.zgq.component.web.config;

import cool.zgq.component.web.properties.SwaggerProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description Swagger配置类
 */
@Configuration
@Import({SwaggerProperties.class})
@EnableSwagger2WebMvc
@AllArgsConstructor
@ConditionalOnProperty(name = "zgq.swagger.enable", havingValue = "true")
public class SwaggerConfig {

    @Resource
    private final SwaggerProperties swaggerProperties;

    @Bean(value = "dockerBean")
    public Docket dockerBean() {

        List<Parameter> pars = new ArrayList<>();

        //指定使用Swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //这里一定要标注你控制器的位置
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build()
                .enable(swaggerProperties.isEnable())
                .globalOperationParameters(pars);
    }
    /**
     * 构造API信息
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getServiceUrl())
                .contact(new Contact(swaggerProperties.getContactName(),swaggerProperties.getContactUrl(),swaggerProperties.getContactEmail()))
                .version("1.0")
                .build();
    }
}