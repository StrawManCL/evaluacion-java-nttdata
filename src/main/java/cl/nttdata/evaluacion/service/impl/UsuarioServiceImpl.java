package cl.nttdata.evaluacion.service.impl;

import cl.nttdata.evaluacion.dto.TelefonoDTO;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import cl.nttdata.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.nttdata.evaluacion.model.Phone;
import cl.nttdata.evaluacion.model.User;
import cl.nttdata.evaluacion.repository.UserRepository;
import cl.nttdata.evaluacion.service.UserService;
import cl.nttdata.evaluacion.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<UsuarioResponseDTO> findAllUsers() {
        return userRepository.findAll()
                .stream().map(UsuarioResponseDTO::fromUser)
                .toList();
    }

    @Override
    public Optional<UsuarioFullResponseDTO> findUserById(UUID id) {
        return userRepository.findById(id)
                .map(UsuarioFullResponseDTO::fromUser);
    }

    @Override
    public UsuarioResponseDTO createUser(UsuarioRequestDTO usuarioRequestDTO) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(usuarioRequestDTO.email())) {
            throw new EmailAlreadyExistsException("El correo ya está registrado");
        }
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(usuarioRequestDTO.name())
                .email(usuarioRequestDTO.email())
                .password(usuarioRequestDTO.password())
                .phones(toPhones(usuarioRequestDTO.phones()))
                .created(OffsetDateTime.now())
                .modified(OffsetDateTime.now())
                .lastLogin(OffsetDateTime.now())
                .token(generateToken(usuarioRequestDTO.email()))
                .active(true)
                .build();
        userRepository.save(user);

        return UsuarioResponseDTO.fromUser(user);
    }

    private List<Phone> toPhones(List<TelefonoDTO> phones) {
        return phones.stream()
                .map(dto -> Phone.builder()
                        .number(dto.number())
                        .citycode(dto.citycode())
                        .countrycode(dto.countrycode())
                        .build())
                .toList();
    }

    private String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }
}
