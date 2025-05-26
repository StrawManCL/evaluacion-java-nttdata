package cl.nttdata.evaluacion.dto;

import cl.nttdata.evaluacion.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record UserFullResponseDTO(
        @Schema(name = "id", example = "d290f1ee-6c54-4b01-90e6-d701748f0851")
        @JsonProperty("id")
        UUID id,

        @Schema(name = "name", example = "Juan Rodriguez")
        @JsonProperty("nombre")
        String nombre,

        @Schema(name = "correo", example = "juan@rodriguez.org")
        @JsonProperty("correo")
        String correo,

        @Schema(name = "clave", example = "hunter2")
        @JsonProperty("clave")
        String clave,

        @Schema(name = "telefono", example = "[{\"number\": \"1234567\", \"citycode\": \"1\", \"countrycode\": \"57\"}]",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("telefono")
        List<TelefonoDTO> telefono,

        @Schema(name = "creado", example = "2025-05-26T00:00Z")
        @JsonProperty("creado")
        OffsetDateTime creado,

        @Schema(name = "modificado", example = "2025-05-26T00:00Z")
        @JsonProperty("modificado")
        OffsetDateTime modificado,

        @Schema(name = "ultimo_login", example = "2025-05-26T00:00Z")
        @JsonProperty("ultimo_login")
        OffsetDateTime ultimoLogin,

        @Schema(name = "token", example = "abc123def456ghi789")
        @JsonProperty("token")
        String token,

        @Schema(name = "isactive", example = "true")
        @JsonProperty("isactive")
        boolean isactive) {
    public static UserFullResponseDTO fromUser(User user) {
        return new UserFullResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhones().stream()
                        .map(phone -> new TelefonoDTO(phone.getNumber(), phone.getCitycode(), phone.getCountrycode()))
                        .toList(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getToken(),
                user.isActive()
        );
    }
}
