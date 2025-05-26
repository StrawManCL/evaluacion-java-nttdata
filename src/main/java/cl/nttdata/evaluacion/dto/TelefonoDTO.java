package cl.nttdata.evaluacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

public record PhoneDTO(
        @Schema(
            name = "number",
            example = "1234567",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("number")
        String number,

        @Schema(
            name = "citycode",
            example = "1",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("citycode")
        String citycode,

        @Schema(
            name = "countrycode",
            example = "57",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
        @JsonProperty("countrycode")
        String countrycode
) { }
