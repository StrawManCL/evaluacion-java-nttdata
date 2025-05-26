package cl.nttdata.evaluacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record TelefonoDTO(
        @Schema(
            name = "numero",
            example = "1234567",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("numero")
        String numero,

        @Schema(
            name = "codigoCiudad",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("codigoCiudad")
        String codigoCiudad,

        @Schema(
            name = "codigoPais",
            example = "57",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("codigoPais")
        String codigoPais
) { }
