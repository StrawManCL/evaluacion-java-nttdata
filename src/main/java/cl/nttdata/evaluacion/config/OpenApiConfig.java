package cl.nttdata.evaluacion.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  /**
   * Crea y configura una instancia personalizada de {@link OpenAPI} para la documentación de la API
   * de Creación de Usuarios, utilizando especificaciones OpenAPI 3.
   *
   * <p>Esta configuración incluye:</p>
   * <ul>
   *   <li>Esquema de seguridad tipo <strong>Bearer Authentication</strong> con formato JWT.</li>
   *   <li>Información general de la API como título, versión, descripción y contacto.</li>
   * </ul>
   *
   * <p>El esquema de seguridad se define bajo el nombre {@code bearerAuth} y se aplica a
   * toda la documentación de la API para requerir autenticación JWT.</p>
   *
   * @return una instancia de {@link OpenAPI} configurada con seguridad y metadata.
   */
  @Bean
  public OpenAPI customOpenAPI() {
    final String securitySchemeName = "bearerAuth";
    final String apiTitle = "API Creación Usuarios";
    final String apiVersion = "1.0.0";
    final String apiDescription = "API Creación Usuarios";
    final String contactName = "Juan Andrés Pardo Rebolledo";
    final String contactEmail = "juan.pardo@gmail.com";

    return new OpenAPI().addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
        .components(new Components().addSecuritySchemes(securitySchemeName,
            new SecurityScheme().name(securitySchemeName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")))
        .info(new Info().title(apiTitle)
            .version(apiVersion)
            .contact(new Contact().name(contactName)
                .email(contactEmail))
            .description(apiDescription));
  }
}