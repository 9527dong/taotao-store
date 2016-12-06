package com.taotao.manage.datasource;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;

/**
 * 定义数据源的AOP切面，通过该Service的方法名判断是应该走读库还是写库
 * 
 * @author zhijun
 *
 */
public class DataSourceAspect {

    /**
     * 在进入Service方法之前执行
     * 
     * @param point 切面对象
     */
    public void before(JoinPoint point) {
        // 获取到当前执行的方法名
        String methodName = point.getSignature().getName();
        if (isSlave(methodName)) {
            // 标记为读库
            DynamicDataSourceHolder.markSlave();
        } else {
            // 标记为写库
            DynamicDataSourceHolder.markMaster();
        }
    }

    /**
     * 判断是否为读库
     * 
     * @param methodName
     * @return
     */
    private Boolean isSlave(String methodName) {
        // 方法名以query、find、get开头的方法名走从库
        return StringUtils.startsWithAny(methodName, "query", "find", "get");
    }

}
