package cl.nttdata.evaluacion.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "validation.regex")
@Getter
@Setter
public class RegexValidator {
  private String correo;
  private String clave;
}