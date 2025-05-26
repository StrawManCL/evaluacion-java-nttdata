package cl.nttdata.evaluacion.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class UserTest {

  private static final OffsetDateTime FECHA = OffsetDateTime.of(LocalDate.of(1970, 1, 1),
      LocalTime.MIDNIGHT, ZoneOffset.UTC);

  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNombre("Juan");
    usuario.setCorreo("juan@rodriguez.org");
    usuario.setTelefono(Collections.emptyList());
    usuario.setClave("hunter2");
    usuario.setCreado(FECHA);
    usuario.setModificado(FECHA);
    usuario.setUltimoLogin(FECHA);
    usuario.setActivo(true);
    usuario.setToken("ABC123");

    int expectedHashCodeResult = usuario.hashCode();
    assertEquals(expectedHashCodeResult, usuario.hashCode());
  }

  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNombre("Juan");
    usuario.setCorreo("juan@rodriguez.org");
    usuario.setTelefono(Collections.emptyList());
    usuario.setClave("hunter2");
    usuario.setCreado(FECHA);
    usuario.setModificado(FECHA);
    usuario.setUltimoLogin(FECHA);
    usuario.setActivo(true);
    usuario.setToken("ABC123");

    Usuario usuario2 = new Usuario();
    usuario2.setId(UUID.randomUUID());
    usuario2.setNombre("Juan");
    usuario2.setCorreo("juan@rodriguez.org");
    usuario2.setTelefono(Collections.emptyList());
    usuario2.setClave("hunter2");
    usuario2.setCreado(FECHA);
    usuario2.setModificado(FECHA);
    usuario2.setUltimoLogin(FECHA);
    usuario2.setActivo(false);
    usuario2.setToken("ABC123");

    assertNotEquals(usuario, usuario2);
  }

  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNombre("Juan");
    usuario.setCorreo("juan@rodriguez.org");
    usuario.setTelefono(Collections.emptyList());
    usuario.setClave("hunter2");
    usuario.setCreado(FECHA);
    usuario.setModificado(FECHA);
    usuario.setUltimoLogin(FECHA);
    usuario.setActivo(true);
    usuario.setToken("ABC123");

    assertNotEquals(null, usuario);
  }

  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNombre("Juan");
    usuario.setCorreo("juan@rodriguez.org");
    usuario.setTelefono(Collections.emptyList());
    usuario.setClave("hunter2");
    usuario.setCreado(FECHA);
    usuario.setModificado(FECHA);
    usuario.setUltimoLogin(FECHA);
    usuario.setActivo(true);
    usuario.setToken("ABC123");

    assertNotEquals("Otro Objeto", usuario);
  }

  @Test
  void testGettersAndSetters() {
    UUID id = UUID.randomUUID();
    Usuario usuario = new Usuario();
    usuario.setId(UUID.randomUUID());
    usuario.setNombre("Juan");
    usuario.setCorreo("juan@rodriguez.org");
    usuario.setTelefono(Collections.emptyList());
    usuario.setClave("hunter2");
    usuario.setCreado(FECHA);
    usuario.setModificado(FECHA);
    usuario.setUltimoLogin(FECHA);
    usuario.setActivo(true);
    usuario.setToken("ABC123");

    assertEquals("ABC123", usuario.getToken());
    assertEquals("Juan", usuario.getNombre());
    assertEquals("hunter2", usuario.getClave());
    assertEquals("juan@rodriguez.org", usuario.getCorreo());
    assertTrue(usuario.isActivo());
    assertTrue(usuario.getTelefono()
        .isEmpty());
    assertSame(FECHA, usuario.getCreado());
    assertSame(FECHA, usuario.getUltimoLogin());
    assertSame(FECHA, usuario.getModificado());
    assertSame(id, usuario.getId());
  }
}
