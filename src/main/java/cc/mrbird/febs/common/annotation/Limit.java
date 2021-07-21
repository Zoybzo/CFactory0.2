package cc.mrbird.febs.common.annotation;


import cc.mrbird.febs.common.entity.LimitType;
import cc.mrbird.febs.common.entity.Strings;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author MrBird
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     * 资源名称，用于描述接口功能
     *
     * @return String
     */
    String name() default Strings.EMPTY;

    /**
     * 资源 key
     *
     * @return String
     */
    String key() default Strings.EMPTY;

    /**
     * key prefix
     *
     * @return String
     */
    String prefix() default Strings.EMPTY;

    /**
     * 时间范围，单位秒
     *
     * @return int
     */
    int period();

    /**
     * 限制访问次数
     *
     * @return int
     */
    int count();

    /**
     * 限制类型
     *
     * @return Type
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
