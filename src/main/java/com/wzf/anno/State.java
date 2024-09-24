package com.wzf.anno;

import com.wzf.validation.StateValidation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;

@Documented//元注解
@Constraint(validatedBy = {StateValidation.class})//指定提供校验规则的类
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)

public @interface State {
    String message() default "state参数的值只能是已发布或者草稿";

    //指定分组
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
