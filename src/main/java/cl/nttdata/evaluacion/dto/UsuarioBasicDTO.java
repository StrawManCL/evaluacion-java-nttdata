package cl.nttdata.evaluacion.dto;

import cl.nttdata.evaluacion.model.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.UUID;

public record UsuarioBasicDTO(
    @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
    @JsonProperty("id")
    UUID id,

    @Schema(name = "creado", example = "2025-05-26T00:00Z")
    @JsonProperty("creado")
    OffsetDateTime creado,

    @Schema(name = "modificado", example = "2025-05-26T00:00Z")
    @JsonProperty("modificado")
    OffsetDateTime modificado,

    @Schema(name = "ultimoLogin", example = "2025-05-26T00:00Z")
    @JsonProperty("ultimoLogin")
    OffsetDateTime ultimoLogin,

    @Schema(name = "token", example = "abc123def456ghi789")
    @JsonProperty("token")
    String token,

    @Schema(name = "activo", example = "true")
    @JsonProperty("activo")
    boolean isActivo
) {
  public static UsuarioBasicDTO fromUser(Usuario user) {
    return new UsuarioBasicDTO(
        user.getId(),
        user.getCreado(),
        user.getModificado(),
        user.getUltimoLogin(),
        user.getToken(),
        user.isActivo()
    );
  }
}
