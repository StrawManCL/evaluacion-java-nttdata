package cl.nttdata.evaluacion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
  /**
   * Sobrescribe método para agregar configuraciones de CORS al registro de la aplicación.
   *
   * <p>Esta configuración permite solicitudes CORS desde cualquier origen ({@code *})
   * a cualquier endpoint ({@code /**}), con los siguientes métodos HTTP habilitados:
   * {@code GET}, {@code PUT}, {@code POST}, {@code DELETE} y {@code PATCH}.</p>
   *
   * <p>Se permiten los encabezados {@code Content-Type} y {@code Authorization},
   * y se establece una duración máxima de 1800 segundos (30 minutos).</p>
   *
   * @param registry el registro de CORS al que se le agregan las reglas de configuración.
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("*")
        .allowedMethods("GET", "PUT", "POST", "DELETE", "PATCH")
        .allowedHeaders("Content-Type", "Authorization")
        .maxAge(1800);
  }
}