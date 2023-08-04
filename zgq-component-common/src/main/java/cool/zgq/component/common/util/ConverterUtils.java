package cool.zgq.component.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: GQ
 * @Date: 2023/11/26 11:25
 * @Description: Bean拷贝、List对象拷贝、支持分页
 */
public class ConverterUtils {

    /**
     * bean拷贝
     * @param v source 类型
     * @param beanClass 目标类 Class
     * @param <V>
     * @param <C>
     * @return
     */
    public static <V, C> C convert(V v, Class<C> beanClass) {
        if (ObjectUtils.isEmpty(v)) {
            return null;
        } else {
            C target = null;

            try {
                target = beanClass.newInstance();
                BeanUtils.copyProperties(v, target);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return target;
        }
    }


    /**
     * list转换 同时兼容 github的Page转换
     * @param vList
     * @param beanClass
     * @param <V>
     * @param <C>
     * @return
     */
    public static <V, C> List<C> convertList(List<V> vList, Class<C> beanClass) {
        if(vList instanceof  com.github.pagehelper.Page) {
            com.github.pagehelper.Page<C> cList = new com.github.pagehelper.Page();
            if (CollectionUtils.isEmpty(vList)) {
                return cList;
            } else {
                com.github.pagehelper.Page vPage = ( com.github.pagehelper.Page)vList;
                cList.setPageSize(vPage.getPageSize());
                cList.setPageNum(vPage.getPageNum());
                cList.setPages(vPage.getPages());
                cList.setTotal(vPage.getTotal());

                vList.stream().forEach((v) -> {
                    cList.add(convert(v, beanClass));
                });
                return cList;
            }
        }else{
            List<C> cList = new ArrayList();
            if (CollectionUtils.isEmpty(vList)) {
                return cList;
            } else {
                vList.stream().forEach((v) -> {
                    cList.add(convert(v, beanClass));
                });
                return cList;
            }
        }


    }

    /**
     * 转换 baomiduo 的 Page
     * @param vPage
     * @param beanClass
     * @param <V>
     * @param <C>
     * @return
     */
    public static <V, C> Page<C> convertPage(IPage<V> vPage, Class<C> beanClass) {
        Page<C> cPage = new Page();
        cPage.setSize(vPage.getSize());
        cPage.setCurrent(vPage.getCurrent());
        cPage.setPages(vPage.getPages());
        cPage.setTotal(vPage.getTotal());
        List<V> vList = vPage.getRecords();
        List<C> cList = convertList(vList, beanClass);
        cPage.setRecords(cList);
        return cPage;
    }
}
