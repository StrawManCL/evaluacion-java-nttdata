package cl.nttdata.evaluacion.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.nttdata.evaluacion.dto.TelefonoDTO;
import cl.nttdata.evaluacion.dto.UsuarioBasicDTO;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import cl.nttdata.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.nttdata.evaluacion.fixtures.UsuarioFixture;
import cl.nttdata.evaluacion.model.Usuario;
import cl.nttdata.evaluacion.repository.UsuarioRepository;
import cl.nttdata.evaluacion.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UsuarioServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UsuarioServiceImplTest {

  private static final UUID ID_USUARIO = UUID.randomUUID();
  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @MockitoBean
  private JwtUtil jwtUtil;
  @MockitoBean
  private UsuarioRepository userRepository;
  @Autowired
  private UsuarioServiceImpl userServiceImpl;

  @Test
  void testFindAllUsers() {
    when(userRepository.findAll()).thenReturn(new ArrayList<>());

    List<UsuarioResponseDTO> actualFindAllUsersResult = userServiceImpl.findAll();

    verify(userRepository).findAll();
    assertTrue(actualFindAllUsersResult.isEmpty());
  }

  @Test
  void testFindAllUsers2() {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    List<Usuario> userList = new ArrayList<>();
    userList.add(usuario);
    when(userRepository.findAll()).thenReturn(userList);

    List<UsuarioResponseDTO> actualFindAllUsersResult = userServiceImpl.findAll();

    verify(userRepository).findAll();
    assertEquals(1, actualFindAllUsersResult.size());
    UsuarioResponseDTO getResult = actualFindAllUsersResult.getFirst();
    assertTrue(getResult.activo());
    assertSame(FECHA, getResult.ultimoLogin());
    assertSame(ID_USUARIO, getResult.id());
  }

  @Test
  void testFindAllUsers4() {
    when(userRepository.findAll()).thenThrow(new EmailAlreadyExistsException("An error occurred"));

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.findAll());
    verify(userRepository).findAll();
  }

  @Test
  void testFindUserById() {
    UUID id = UUID.randomUUID();
    OffsetDateTime fecha = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);

    Optional<Usuario> usuario = Optional.of(UsuarioFixture.usuarioSinTelefono(id, fecha));

    when(userRepository.findById(Mockito.any())).thenReturn(usuario);

    Optional<UsuarioFullResponseDTO> findByIdResult = userServiceImpl.findById(UUID.randomUUID());

    verify(userRepository).findById(isA(UUID.class));
    assertTrue(findByIdResult.isPresent());
    UsuarioFullResponseDTO getResult = findByIdResult.get();
    assertEquals("ABC123", getResult.token());
    assertEquals("Juan", getResult.nombre());
    assertEquals("hunter2", getResult.clave());
    assertEquals("juan@rodriguez.org", getResult.correo());
    assertTrue(getResult.activo());
    assertTrue(getResult.telefono()
        .isEmpty());
    assertSame(id, getResult.id());
    assertSame(fecha, getResult.creado());
    assertSame(fecha, getResult.modificado());
    assertSame(fecha, getResult.ultimoLogin());
  }

  @Test
  void testCreateUser() throws EmailAlreadyExistsException {
    when(userRepository.existsByCorreo(Mockito.any())).thenReturn(true);

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>())));
    verify(userRepository).existsByCorreo(eq("juan@rodriguez.org"));
  }

  @Test
  void testCreateUser2() throws EmailAlreadyExistsException {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    when(userRepository.existsByCorreo(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>()));

    verify(userRepository).existsByCorreo(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
    verify(userRepository).save(isA(Usuario.class));

    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActivo());
    ZoneOffset offset = actualCreateUserResult.creado()
        .getOffset();
    assertSame(offset, actualCreateUserResult.ultimoLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modificado()
        .getOffset());
  }

  @Test
  void testCreateUser3() throws EmailAlreadyExistsException {
    Usuario usuario = UsuarioFixture.usuarioSinTelefono(ID_USUARIO, FECHA);

    when(userRepository.existsByCorreo(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    ArrayList<TelefonoDTO> phones = new ArrayList<>();
    phones.add(new TelefonoDTO("1234", "2", "56"));

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "iloveyou", phones));

    verify(userRepository).existsByCorreo(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
    verify(userRepository).save(isA(Usuario.class));
    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActivo());
    ZoneOffset offset = actualCreateUserResult.creado()
        .getOffset();
    assertSame(offset, actualCreateUserResult.ultimoLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modificado()
        .getOffset());
  }

  @Test
  void testCreateUser4() throws EmailAlreadyExistsException {
    Usuario usuario = new Usuario();
    usuario.setActivo(true);
    usuario.setCreado(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setCorreo("jane.doe@example.org");
    usuario.setId(UUID.randomUUID());
    usuario.setUltimoLogin(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setModificado(
        OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT, ZoneOffset.UTC));
    usuario.setNombre("Name");
    usuario.setClave("iloveyou");
    usuario.setTelefono(new ArrayList<>());
    usuario.setToken("ABC123");
    when(userRepository.existsByCorreo(Mockito.any())).thenReturn(false);
    when(userRepository.save(Mockito.any())).thenReturn(usuario);
    when(jwtUtil.generateToken(Mockito.any())).thenReturn("ABC123");

    ArrayList<TelefonoDTO> phones = new ArrayList<>();
    phones.add(new TelefonoDTO("42", "Oxford", "GB"));
    phones.add(new TelefonoDTO("42", "Oxford", "GB"));

    UsuarioBasicDTO actualCreateUserResult = userServiceImpl.create(
        new UsuarioRequestDTO("Name", "jane.doe@example.org", "iloveyou", phones));

    verify(userRepository).existsByCorreo(eq("jane.doe@example.org"));
    verify(jwtUtil).generateToken(eq("jane.doe@example.org"));
    verify(userRepository).save(isA(Usuario.class));
    assertEquals("ABC123", actualCreateUserResult.token());
    assertTrue(actualCreateUserResult.isActivo());
    ZoneOffset offset = actualCreateUserResult.creado()
        .getOffset();
    assertSame(offset, actualCreateUserResult.ultimoLogin()
        .getOffset());
    assertSame(offset, actualCreateUserResult.modificado()
        .getOffset());
  }

  @Test
  void testCreateUser5() throws EmailAlreadyExistsException {
    when(userRepository.existsByCorreo(Mockito.any())).thenReturn(false);
    when(jwtUtil.generateToken(Mockito.any())).thenThrow(
        new EmailAlreadyExistsException("An error occurred"));

    assertThrows(EmailAlreadyExistsException.class, () -> userServiceImpl.create(
        new UsuarioRequestDTO("Juan", "juan@rodriguez.org", "hunter2", new ArrayList<>())));
    verify(userRepository).existsByCorreo(eq("juan@rodriguez.org"));
    verify(jwtUtil).generateToken(eq("juan@rodriguez.org"));
  }
}
