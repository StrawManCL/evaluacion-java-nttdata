package cl.nttdata.evaluacion.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class TelefonoTest {

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumero("1234");
    telefono2.setCodigoCiudad("2");
    telefono2.setCodigoPais("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual2() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad(null);
    telefono.setCodigoPais("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumero("1234");
    telefono2.setCodigoCiudad(null);
    telefono2.setCodigoPais("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual3() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais(null);

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumero("1234");
    telefono2.setCodigoCiudad("2");
    telefono2.setCodigoPais(null);

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual4() {
    Telefono telefono = new Telefono();
    telefono.setId(null);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(null);
    telefono2.setNumero("1234");
    telefono2.setCodigoCiudad("2");
    telefono2.setCodigoPais("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsEqual_thenReturnEqual5() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero(null);
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumero(null);
    telefono2.setCodigoCiudad("2");
    telefono2.setCodigoPais("56");

    assertEquals(telefono, telefono2);
    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono2.hashCode());
  }

  @Test
  void testEqualsAndHashCode_whenOtherIsSame_thenReturnEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    int expectedHashCodeResult = telefono.hashCode();
    assertEquals(expectedHashCodeResult, telefono.hashCode());
  }

  @Test
  void testEquals_whenOtherIsDifferent_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    Telefono telefono2 = new Telefono();
    telefono2.setId(1L);
    telefono2.setNumero("12345");
    telefono2.setCodigoCiudad("2");
    telefono2.setCodigoPais("563");

    assertNotEquals(telefono, telefono2);
  }

  @Test
  void testEquals_whenOtherIsNull_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    assertNotEquals(null, telefono);
  }

  @Test
  void testEquals_whenOtherIsWrongType_thenReturnNotEqual() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    assertNotEquals("Objeto diferente a Telefono", telefono);
  }

  @Test
  void testGettersAndSetters() {
    Telefono telefono = new Telefono();
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    assertEquals("1234", telefono.getNumero());
    assertEquals("2", telefono.getCodigoCiudad());
    assertEquals("56", telefono.getCodigoPais());
    assertEquals("Telefono(id=1, numero=1234, codigoCiudad=2, codigoPais=56)",
        telefono.toString());
    assertEquals(1L, telefono.getId());
  }

  @Test
  void testGettersAndSetters2() {
    Telefono telefono = new Telefono(1L, "1234", "2", "56");
    telefono.setId(1L);
    telefono.setNumero("1234");
    telefono.setCodigoCiudad("2");
    telefono.setCodigoPais("56");

    assertEquals("1234", telefono.getNumero());
    assertEquals("2", telefono.getCodigoCiudad());
    assertEquals("56", telefono.getCodigoPais());
    assertEquals("Telefono(id=1, numero=1234, codigoCiudad=2, codigoPais=56)",
        telefono.toString());
    assertEquals(1L, telefono.getId());
  }
}
