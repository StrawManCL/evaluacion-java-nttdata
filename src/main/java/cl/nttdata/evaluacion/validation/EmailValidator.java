package cl.nttdata.evaluacion.util;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

  private final ValidationRegexProperties regexProperties;

  public EmailValidator(ValidationRegexProperties regexProperties) {
    this.regexProperties = regexProperties;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) return false;
    return value.matches(regexProperties.getCorreo());
  }
}