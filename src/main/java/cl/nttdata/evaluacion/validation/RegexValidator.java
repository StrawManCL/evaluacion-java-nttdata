package cl.nttdata.evaluacion.validation;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "validation.regex")
public class RegexValidator {

  private String correo;
  private String clave;
}