package cl.nttdata.evaluacion.fixtures;

import cl.nttdata.evaluacion.model.Telefono;
import java.util.Collections;
import java.util.List;

public class TelefonoFixture {

  public static List<Telefono> telefonoUnico() {
    return List.of(defaulTelefono(1));
  }

  public static List<Telefono> tresTelefonos() {
    return List.of(defaulTelefono(1), defaulTelefono(2), defaulTelefono(3));
  }

  public static List<Telefono> sinTelefono() {
    return Collections.emptyList();
  }

  public static Telefono defaulTelefono(long id) {
    return Telefono.builder()
        .id(id)
        .numero("1234567")
        .codigoCiudad("1")
        .codigoPais("57")
        .build();
  }
}
