package cl.nttdata.evaluacion.dto;

import cl.nttdata.evaluacion.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UsuarioResponseDTO(
    @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @JsonProperty("id")
    UUID id,

    @Schema(name = "nombre", example = "Juan Rodriguez")
    @JsonProperty("nombre")
    String nombre,

    @Schema(name = "correo", example = "juan@rodriguez.org")
    @JsonProperty("correo")
    String correo,

    @Schema(name = "telefono", example = "[{\"number\": \"1234567\", \"citycode\": \"1\", \"countrycode\": \"57\"}]", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("telefono")
    List<TelefonoDTO> telefono,

    @Schema(name = "ultimo_login", example = "2025-05-26T00:00Z")
    @JsonProperty("ultimoLogin")
    OffsetDateTime ultimoLogin,

    @Schema(name = "activo", example = "true")
    @JsonProperty("activo")
    boolean activo
) {
  public static UsuarioResponseDTO fromUser(Usuario usuario) {
    return new UsuarioResponseDTO(
        usuario.getId(),
        usuario.getNombre(),
        usuario.getCorreo(),
        usuario.getTelefono()
            .stream()
            .map(telefono -> new TelefonoDTO(
                telefono.getNumero(),
                telefono.getCodigoCiudad(),
                telefono.getCodigoPais()
            ))
            .toList(),
        usuario.getUltimoLogin(),
        usuario.isActivo());
  }
}
