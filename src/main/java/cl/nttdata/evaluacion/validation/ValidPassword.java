package cl.nttdata.evaluacion.util;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassword {
  String message() default "Contraseña inválida";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}