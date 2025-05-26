package cl.nttdata.evaluacion.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private final RegexValidator regexValidator;

  public EmailValidator(RegexValidator regexValidator) {
    this.regexValidator = regexValidator;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }
    return value.matches(regexValidator.getCorreo());
  }
}