package cl.nttdata.evaluacion.service;

import cl.nttdata.evaluacion.dto.UsuarioBasicDTO;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UsuarioService {

  List<UsuarioResponseDTO> findAll();

  Optional<UsuarioFullResponseDTO> findById(UUID id);

  UsuarioBasicDTO create(UsuarioRequestDTO user);
}
