package cl.nttdata.evaluacion.service.impl;

import cl.nttdata.evaluacion.dto.TelefonoDTO;
import cl.nttdata.evaluacion.dto.UsuarioBasicDTO;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import cl.nttdata.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.nttdata.evaluacion.model.Telefono;
import cl.nttdata.evaluacion.model.Usuario;
import cl.nttdata.evaluacion.repository.UsuarioRepository;
import cl.nttdata.evaluacion.service.UsuarioService;
import cl.nttdata.evaluacion.util.JwtUtil;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final JwtUtil jwtUtil;

  @Override
  public List<UsuarioResponseDTO> findAll() {
    return usuarioRepository.findAll()
        .stream()
        .map(UsuarioResponseDTO::fromUser)
        .toList();
  }

  @Override
  public Optional<UsuarioFullResponseDTO> findById(UUID id) {
    return usuarioRepository.findById(id)
        .map(UsuarioFullResponseDTO::fromUser);
  }

  @Override
  public UsuarioBasicDTO create(UsuarioRequestDTO usuarioRequestDTO)
      throws EmailAlreadyExistsException {
    if (usuarioRepository.existsByCorreo(usuarioRequestDTO.correo())) {
      throw new EmailAlreadyExistsException("El correo ya está registrado");
    }
    List<Telefono> telefonos = telefonoDtoToEntity(usuarioRequestDTO.telefono());

    Usuario user = Usuario.builder()
        .nombre(usuarioRequestDTO.nombre())
        .correo(usuarioRequestDTO.correo())
        .clave(usuarioRequestDTO.clave())
        .telefono(telefonos)
        .creado(OffsetDateTime.now())
        .modificado(OffsetDateTime.now())
        .ultimoLogin(OffsetDateTime.now())
        .token(generateToken(usuarioRequestDTO.correo()))
        .activo(true)
        .build();
    usuarioRepository.save(user);

    return UsuarioBasicDTO.fromUser(user);
  }

  private List<Telefono> telefonoDtoToEntity(List<TelefonoDTO> telefonoDTOList) {
    return telefonoDTOList.stream()
        .map(dto -> Telefono.builder()
            .numero(dto.numero())
            .codigoCiudad(dto.codigoCiudad())
            .codigoPais(dto.codigoPais())
            .build())
        .toList();
  }

  private String generateToken(String email) {
    return jwtUtil.generateToken(email);
  }
}
