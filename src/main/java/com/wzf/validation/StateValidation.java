package com.wzf.validation;

import com.wzf.anno.State;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {

    /**
     *
     * @param string 将来要校验的数据
     * @param constraintValidatorContext
     * @return 如果返回false,则校验不通过，如果返回true，则校验通过
     */
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        //提供校验规则
        if (string == null){
            return false;
        }
        if ("已发布".equals(string) || "草稿".equals(string)) return true;
        return false;
    }
}
