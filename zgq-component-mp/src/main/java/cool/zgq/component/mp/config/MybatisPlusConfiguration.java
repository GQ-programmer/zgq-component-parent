package cool.zgq.component.mp.config;

import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.github.pagehelper.PageInterceptor;
import cool.zgq.component.mp.handler.MyMetaObjectHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import cool.zgq.component.mp.page.EnablePageAspect;

import java.util.Properties;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 19:07
 * @Description 注册分页、乐观锁相关Bean
 */
@Component
@Import({MyMetaObjectHandler.class})
public class MybatisPlusConfiguration {

    /**
     * 乐观锁
     */
    @Bean
    @ConditionalOnMissingBean
    public OptimisticLockerInnerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInnerInterceptor();
    }

    /**
     * 分页插件 github 的pageHelper 不是 mybatis plus
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public PageInterceptor paginationInterceptor() {
        PageInterceptor pageHelper = new PageInterceptor();
        Properties p = new Properties();
        p.put("offsetAsPageNum", true);
        p.put("rowBoundsWithCount", true);
        pageHelper.setProperties(p);
        return pageHelper;
    }

    /**
     * 分页注解切面
     *
     * @return
     */
    @Bean
    public EnablePageAspect enablePageAspect() {
        return new EnablePageAspect();
    }

}
