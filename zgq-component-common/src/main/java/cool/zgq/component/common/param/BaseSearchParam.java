package cool.zgq.component.common.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author GQ
 * @date 2023 /24 12:12
 * @Description 基础参数类
 */
@Data
public class BaseSearchParam implements Serializable {

    private static final long serialVersionUID = 1500479591865674626L;

    @ApiModelProperty("页码")
    private Integer page;

    @ApiModelProperty("一页的数量")
    private Integer size;

    @ApiModelProperty("公共查找条件，多个字段统一查询字段")
    private String searchField;

    @ApiModelProperty("排序字段")
    private String sortField;

    @ApiModelProperty("是否正序")
    private Boolean isDesc = false;

}
