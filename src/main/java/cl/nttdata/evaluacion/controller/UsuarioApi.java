package cl.nttdata.evaluacion.controller;

import cl.nttdata.evaluacion.dto.UsuarioBasicDTO;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
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
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@Tag(name = "Usuario", description = "Funcionalidades de administración de usuarios.")
public interface UsuarioApi {
    /**
     * POST /api/usuario : Crea un nuevo usuario
     *
     * @param UsuarioRequestDTO (required)
     * @return Usuario creado (status code 201)
     */
    @Operation(operationId = "crearUsuario", summary = "Crea un nuevo usuario", tags = {"Usuario"})
    @ApiResponse(responseCode = "201", description = "Usuario creado", content = {
        @Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = UsuarioBasicDTO.class)))})
    @PostMapping(value = "/usuario", consumes = {"application/json"})
    @PreAuthorize("permitAll()")
    default ResponseEntity<?> crearUsuario(
            @Parameter(name = "UsuarioRequestDTO", description = "Usuario a crear", required = true)
            @Valid @RequestBody UsuarioRequestDTO UsuarioRequestDTO) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /api/usuario : Lista todos los usuarios
     *
     * @return Lista de usuarios (status code 200)
     */
    @Operation(operationId = "listaUsuarios", summary = "Lista todos los usuarios creados", tags = {"Usuario"})
    @ApiResponse(responseCode = "200", description = "Lista de usuarios", content = {
            @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDTO.class)))})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/usuario", produces = {"application/json"})
    default ResponseEntity<List<UsuarioResponseDTO>> listaUsuarios() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    /**
     * GET /api/usuario/{id} : Obtiene los detalles de un usuario específico
     *
     * @param id (required)
     * @return Detalles del usuario (status code 200)
     */
    @Operation(operationId = "getUsuario", summary = "Obtiene los detalles de un usuario específico", tags = {"Usuario"})
    @ApiResponse(responseCode = "200", description = "Detalles del usuario",
            content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioFullResponseDTO.class))})
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping(value = "/usuario/{id}", produces = {"application/json"})
    default ResponseEntity<UsuarioFullResponseDTO> getUsuario(
            @Parameter(name = "id", description = "Id de Usuario", required = true, in = ParameterIn.PATH)
            @PathVariable("id") UUID id) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
}
