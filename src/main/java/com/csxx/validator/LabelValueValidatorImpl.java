package com.csxx.validator;

import com.csxx.dto.webOrg.form.LabelValue;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class LabelValueValidatorImpl implements ConstraintValidator<LabelValueConstraint, List<LabelValue>> {

    @Override
    public void initialize(LabelValueConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(List<LabelValue> value, ConstraintValidatorContext context) {
        boolean isValid = true;
        if (value == null || value.size() == 0) {
            isValid = false;
        } else {
            for (LabelValue labelValue : value) {
                if (StringUtils.isBlank(labelValue.getValue())) {
                    isValid = false;
                    break;
                }
            }
        }
        return isValid;
    }
}
