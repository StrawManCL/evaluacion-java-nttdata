package cl.nttdata.evaluacion.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  private final RegexValidator regexValidator;

  public PasswordValidator(RegexValidator regexValidator) {
    this.regexValidator = regexValidator;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return value.matches(regexValidator.getClave());
  }
}