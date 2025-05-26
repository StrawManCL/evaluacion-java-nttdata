package cl.nttdata.evaluacion.controller;

import cl.nttdata.evaluacion.dto.UserFullResponseDTO;
import cl.nttdata.evaluacion.dto.UserRequestDTO;
import cl.nttdata.evaluacion.dto.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Validated
@Tag(name = "User", description = "Funcionalidades de administración de usuarios.")
public interface UserApi {
    /**
     * POST /api/users : Crea un nuevo usuario
     *
     * @param userRequestDTO (required)
     * @return Usuario creado (status code 201)
     */
    @Operation(operationId = "createUser", summary = "Crea un nuevo usuario", tags = {"User"})
    @ApiResponse(responseCode = "201", description = "Usuario creado")
    @PostMapping(value = "/users", consumes = {"application/json"})
    @PreAuthorize("permitAll()")
    default ResponseEntity<?> createUser(
            @Parameter(name = "UserRequestDTO", description = "Usuario a crear", required = true)
            @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /api/users : Lista todos los usuarios
     *
     * @return Lista de usuarios (status code 200)
     */
    @Operation(operationId = "getAllUsers", summary = "Lista todos los usuarios", tags = {"User"})
    @ApiResponse(responseCode = "200", description = "Lista de usuarios", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class)))})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/users", produces = {"application/json"})
    default ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /api/users/{id} : Obtiene los detalles de un usuario específico
     *
     * @param id (required)
     * @return Detalles del usuario (status code 200)
     */
    @Operation(operationId = "getUser", summary = "Obtiene los detalles de un usuario específico", tags = {"User"})
    @ApiResponse(responseCode = "200", description = "Detalles del usuario",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserFullResponseDTO.class))})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/users/{id}", produces = {"application/json"})
    default ResponseEntity<UserFullResponseDTO> getUser(
            @Parameter(name = "id", description = "Id de Usuario", required = true, in = ParameterIn.PATH)
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
