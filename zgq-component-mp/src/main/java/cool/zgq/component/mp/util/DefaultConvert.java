package cool.zgq.component.mp.util;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:14
 * @Description
 */
public class DefaultConvert extends MySqlTypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {

        if(fieldType.equals("datetime")) {
            return DbColumnType.DATE;
        }

        if(fieldType.equals("date")) {
            return DbColumnType.DATE;
        }

        return super.processTypeConvert(globalConfig,fieldType);
    }


}
