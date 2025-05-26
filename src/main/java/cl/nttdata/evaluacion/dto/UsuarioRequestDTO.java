package cl.nttdata.evaluacion.dto;

import cl.nttdata.evaluacion.validation.ValidEmail;
import cl.nttdata.evaluacion.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioRequestDTO(
        @Schema(name = "nombre", example = "Juan Rodriguez", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("nombre")
        @NotBlank(message = "Nombre es obligatorio")
        String nombre,

        @Schema(name = "correo", example = "juan@rodriguez.org", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Correo electrónico válido (por ejemplo, juan@rodriguez.org)")
        @JsonProperty("correo")
        @ValidEmail
        String correo,

        @Schema(name = "contraseña", example = "hunter2", requiredMode = Schema.RequiredMode.REQUIRED,
            description = "Contraseña debe contener al menos 8 caracteres, una mayúscula, una minúscula y un número")
        @JsonProperty("contraseña")
        @ValidPassword
        String clave,

        @Schema(name = "telefonos", example = "[{\"numero\": \"1234567\", \"codigoCiudad\": \"1\", \"codigoPais\": \"57\"}]",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("telefonos")
        List<TelefonoDTO> telefono
) { }