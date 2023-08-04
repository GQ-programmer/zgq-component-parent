package cool.zgq.component.common.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 11:02
 * @Description K、V
 */
@Data
public class KeyValueVO implements Serializable {

    private String key;

    private String value;
}