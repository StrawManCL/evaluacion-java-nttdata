package cl.nttdata.evaluacion.service;

import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UsuarioResponseDTO> findAllUsers();

    Optional<UsuarioFullResponseDTO> findUserById(UUID id);

    UsuarioResponseDTO createUser(UsuarioRequestDTO user);
}
