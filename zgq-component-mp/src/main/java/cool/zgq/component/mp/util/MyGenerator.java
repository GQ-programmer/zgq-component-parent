package cool.zgq.component.mp.util;


import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:14
 * @Description
 */
public class MyGenerator extends AutoGenerator {

    /**
     * 构造方法
     *
     * @param dataSourceConfig 数据库配置
     * @since 3.5.0
     */
    public MyGenerator(@NotNull DataSourceConfig dataSourceConfig) {
        super(dataSourceConfig);
    }

    /**
     * 解决换行问题
     * @param config
     * @return
     */
    @Override
    protected List<TableInfo> getAllTableInfoList(ConfigBuilder config) {
        List<TableInfo> tableInfos =  super.getAllTableInfoList(config);
        tableInfos.forEach(t->{
            t.getFields().forEach(f->{
                if(!StringUtils.isEmpty(f.getComment())) {
                    String comment = f.getComment();
                    //注意，替换换行符
                    comment = comment.replaceAll("\r\n" , "");
                    comment = comment.replaceAll("\"" , "'");
                    f.setComment(comment);
                }
            });
        });
        return tableInfos;
    }
}
