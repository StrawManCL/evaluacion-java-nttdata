package cl.nttdata.evaluacion.controller.impl;

import cl.nttdata.evaluacion.controller.UsuarioApi;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import cl.nttdata.evaluacion.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController implements UsuarioApi {

  private final UsuarioService usuarioService;

  @Override
  @PreAuthorize("permitAll()")
  public ResponseEntity<?> crearUsuario(UsuarioRequestDTO usuarioRequestDTO) {
    UsuarioResponseDTO usuarioCreado = usuarioService.create(usuarioRequestDTO);
    return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
  }

  @Override
  public ResponseEntity<List<UsuarioResponseDTO>> listaUsuarios() {
    return ResponseEntity.ok(usuarioService.findAll());
  }

  @Override
  public ResponseEntity<UsuarioFullResponseDTO> getUsuario(UUID id) {
    Optional<UsuarioFullResponseDTO> optionalUsuario = usuarioService.findById(id);
    return optionalUsuario.map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.notFound()
            .build());
  }
}
