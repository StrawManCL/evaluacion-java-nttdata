package cl.nttdata.evaluacion.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.nttdata.evaluacion.controller.impl.UserController;
import cl.nttdata.evaluacion.dto.UsuarioFullResponseDTO;
import cl.nttdata.evaluacion.dto.UsuarioRequestDTO;
import cl.nttdata.evaluacion.dto.UsuarioResponseDTO;
import cl.nttdata.evaluacion.model.User;
import cl.nttdata.evaluacion.repository.UserRepository;
import cl.nttdata.evaluacion.service.impl.UsuarioServiceImpl;
import cl.nttdata.evaluacion.util.JwtUtil;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserApi.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserApiTest {

  @MockitoBean
  private UserApi userApi;

  @Test
  void testCreateUser() {
    ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatusCode.valueOf(200));
    Mockito.<ResponseEntity<?>>when(userApi.createUser(Mockito.any()))
        .thenReturn(responseEntity);

    ResponseEntity<?> actualCreateUserResult = userApi.createUser(
        new UsuarioRequestDTO("Name", "jane.doe@example.org", "testtest", new ArrayList<>()));

    verify(userApi).createUser(isA(UsuarioRequestDTO.class));
    assertSame(responseEntity, actualCreateUserResult);
  }

  @Test
  void testGetAllUsers() {
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findAll()).thenReturn(new ArrayList<>());

    ResponseEntity<List<UsuarioResponseDTO>> actualAllUsers = (new UserController(
        new UsuarioServiceImpl(userRepository, new JwtUtil()))).getAllUsers();

    verify(userRepository).findAll();
    HttpStatusCode statusCode = actualAllUsers.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    assertEquals(200, actualAllUsers.getStatusCode()
        .value());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(Objects.requireNonNull(actualAllUsers.getBody())
        .isEmpty());
    assertTrue(actualAllUsers.hasBody());
    assertTrue(actualAllUsers.getHeaders()
        .isEmpty());
  }

  @Test
  void testGetUser() {
    User user = new User();
    user.setActive(true);
    OffsetDateTime created = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);
    user.setCreated(created);
    user.setEmail("jane.doe@example.org");
    UUID id = UUID.randomUUID();
    user.setId(id);
    OffsetDateTime lastLogin = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);
    user.setLastLogin(lastLogin);
    OffsetDateTime modified = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);
    user.setModified(modified);
    user.setName("Name");
    user.setPassword("iloveyou");
    user.setPhones(new ArrayList<>());
    user.setToken("ABC123");
    Optional<User> ofResult = Optional.of(user);
    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findById(Mockito.any())).thenReturn(ofResult);
    UserController userController = new UserController(
        new UsuarioServiceImpl(userRepository, new JwtUtil()));

    ResponseEntity<UsuarioFullResponseDTO> actualUser = userController.getUser(UUID.randomUUID());

    verify(userRepository).findById(isA(UUID.class));
    HttpStatusCode statusCode = actualUser.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    UsuarioFullResponseDTO body = actualUser.getBody();
    assertEquals("ABC123", Objects.requireNonNull(body)
        .token());
    assertEquals("Name", body.name());
    assertEquals("iloveyou", body.password());
    assertEquals("jane.doe@example.org", body.email());
    assertEquals(200, actualUser.getStatusCode()
        .value());
    assertEquals(HttpStatus.OK, statusCode);
    assertTrue(body.isactive());
    assertTrue(body.phones()
        .isEmpty());
    assertTrue(actualUser.hasBody());
    assertTrue(actualUser.getHeaders()
        .isEmpty());
    assertSame(created, body.created());
    assertSame(lastLogin, body.lastLogin());
    assertSame(modified, body.modified());
    assertSame(id, body.id());
  }
}
