package cool.zgq.component.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description 公共实体类
 */
@Data
public abstract class BaseEntity extends Model<BaseEntity> implements Serializable {

    private static final long serialVersionUID = 7906681093334871918L;

    @ApiModelProperty(value = "物理主键")
    @TableId(type = IdType.ASSIGN_ID)
    protected Long id;

    @ApiModelProperty(value = "是否删除")
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer deleted;

    @ApiModelProperty(value = "版本")
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Timestamp createTime;

    @ApiModelProperty(value = "创建人")
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Timestamp updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

}
