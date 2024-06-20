package dongduk.cs.moaread.service.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PasswordValidator implements ConstraintValidator<Password, Object> {
    private String message;
    private String passwordField;
    private String confirmPasswordField;

    @Override
    public void initialize(Password constraintAnnotation) {
        message = constraintAnnotation.message();
        passwordField = constraintAnnotation.password();
        confirmPasswordField = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        boolean flag = true;
        String password = getFieldValue(object, passwordField);
        String confirmPassword = getFieldValue(object, confirmPasswordField);

        if (!password.equals(confirmPassword)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(confirmPasswordField)
                    .addConstraintViolation();
            flag = false;
        }

        return flag;
    }

    private String getFieldValue(Object object, String field) {
        Class<?> clazz = object.getClass();
        Field dataField;

        try {
            dataField = clazz.getDeclaredField(field);
            dataField.setAccessible(true);
            Object target = dataField.get(object);
            if (!(target instanceof String)) {
                throw new RuntimeException();
            }
            return (String) target;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException();
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }
}
