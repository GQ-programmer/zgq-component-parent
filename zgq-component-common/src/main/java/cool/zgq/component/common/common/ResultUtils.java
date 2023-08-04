package cool.zgq.component.common.common;

/**
 * @Author GQ <href="http://www.zgq.cool"/>
 * @Date 2023 07/24 12:20
 * @Description 返回结果工具类
 */
public class ResultUtils {

    /**
     * 返回成功请求
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    public static BaseResponse error(ErrorCode errorCode, String message){
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(int code,String message, String description){
        return new BaseResponse<>(code,null,message,description);
    }
    public static BaseResponse error(ErrorCode errorCode,String message, String description){
        return new BaseResponse<>(errorCode.getCode(),null,message,description);
    }
}
