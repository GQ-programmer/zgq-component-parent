package cool.zgq.component.mp.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import cool.zgq.component.common.util.DateUtil;
import cool.zgq.component.common.util.UserUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 15:28
 * @Description
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    private static final Long DEFAULT_USER_ID = 9999L;

    @Override
    public void insertFill(MetaObject metaObject) {
        Timestamp now = DateUtil.getCurrentDate();
        Long currentUserId = UserUtils.getUserId();

        currentUserId = currentUserId == null? DEFAULT_USER_ID:currentUserId;

        this.setFieldValByName("createBy", currentUserId, metaObject);
        this.setFieldValByName("updateBy", currentUserId, metaObject);
        this.setFieldValByName("createTime", now, metaObject);
        this.setFieldValByName("updateTime", now, metaObject);
        this.setFieldValByName("deleted", 0, metaObject);
        this.setFieldValByName("version", 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        Long currentUserId = UserUtils.getUserId();
        Timestamp currentTime = DateUtil.getCurrentDate();

        if (currentUserId != null) {
            this.setFieldValByName("updateBy", currentUserId, metaObject);
            this.setFieldValByName("updateTime", currentTime, metaObject);
        } else {
            this.setFieldValByName("updateBy", DEFAULT_USER_ID, metaObject);
            this.setFieldValByName("updateTime", currentTime, metaObject);
        }


    }
}
