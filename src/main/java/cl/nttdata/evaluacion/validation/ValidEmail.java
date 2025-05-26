package cl.nttdata.evaluacion.util;

@Documented
@Constraint(validatedBy = EmailValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmail {
  String message() default "Correo inválido";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}