package cl.nttdata.evaluacion.util;

@Component
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

  private final ValidationRegexProperties regexProperties;

  public PasswordValidator(ValidationRegexProperties regexProperties) {
    this.regexProperties = regexProperties;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return false;
    return value.matches(regexProperties.getClave());
  }
}