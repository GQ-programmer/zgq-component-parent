package cool.zgq.component.mp.page;

import com.github.pagehelper.PageHelper;
import cool.zgq.component.common.param.BaseSearchParam;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author GQ
 */
@Aspect
@Component
public class EnablePageAspect {

    @Around("@annotation(cool.zgq.component.mp.page.annotation.EnablePage)")
    public Object invoked(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        //解析 MyPage注解
        Object[] args = proceedingJoinPoint.getArgs();
        for(int i = 0;i<args.length;i++) {
            if( args[i] instanceof BaseSearchParam) {
                BaseSearchParam arg= (BaseSearchParam) args[i];
                if(arg.getPage()!=null && arg.getSize() !=null){
                    PageHelper.startPage(arg.getPage(),arg.getSize());
                }
                break;
            }
        }
        return proceedingJoinPoint.proceed();
    }

}
