package com.ruoyi.schemax.metainfo.anno;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JdbcType {

    String[] value();

}
