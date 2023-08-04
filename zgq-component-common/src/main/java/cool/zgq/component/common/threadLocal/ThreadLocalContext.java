package cool.zgq.component.common.threadLocal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: GQ
 * @Date: 2023/7/24 16:54
 * @Description: ThreadLocal 工具类
 */
public class ThreadLocalContext {


    private static InheritableThreadLocal <Map <String, Object>> threadLocal = new InheritableThreadLocal <>();



    public static Map <String, Object> getContext() {
        Map<String,Object> res = threadLocal.get();

        if (res == null) {
            res = new ConcurrentHashMap <>();
        }
        return res;
    }


    public static void setContext(Map <String, Object> currentAttach) {
        threadLocal.set(currentAttach);
    }

    public static void removeContext() {
        threadLocal.remove();
    }


    public static void putContext(String key, Object value) {
        Map <String, Object> currentAttach = getContext();

        if (currentAttach == null) {
            currentAttach = new ConcurrentHashMap <>();
        }
        currentAttach.put(key, value);
        setContext(currentAttach);
    }

    public static Object getContextValue(String key) {
        Map <String, Object> currentAttach = getContext();
        if (currentAttach == null) {
            return null;
        }
        return currentAttach.get(key);
    }
}
