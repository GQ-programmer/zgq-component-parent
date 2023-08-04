package cool.zgq.component.security.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 08/01 20:37
 * @Description
 */
@Data
public class BaseLoginUser implements Serializable {

    @ApiModelProperty(value = "用户Id")
    private String userId;

    @ApiModelProperty(value = "用户名")
    private String username;

}
