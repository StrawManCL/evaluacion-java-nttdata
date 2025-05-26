package cl.nttdata.evaluacion.fixtures;

import cl.nttdata.evaluacion.model.Usuario;
import io.jsonwebtoken.lang.Strings;
import java.time.OffsetDateTime;
import java.util.UUID;

public class UsuarioFixture {

  public static Usuario usuarioSinTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .nombre("Juan")
        .correo("juan@rodriguez.org")
        .clave("hunter2")
        .activo(true)
        .creado(fecha)
        .modificado(fecha)
        .ultimoLogin(fecha)
        .telefono(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioSinCorreo(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .nombre("Juan")
        .correo(Strings.EMPTY)
        .clave("hunter2")
        .activo(true)
        .creado(fecha)
        .modificado(fecha)
        .ultimoLogin(fecha)
        .telefono(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioConUnTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .nombre("Juan")
        .correo("juan@rodriguez.org")
        .clave("hunter2")
        .activo(true)
        .creado(fecha)
        .modificado(fecha)
        .ultimoLogin(fecha)
        .telefono(TelefonoFixture.telefonoUnico())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioConTresTelefono(UUID id, OffsetDateTime fecha) {
    return Usuario.builder()
        .id(id)
        .nombre("Juan")
        .correo("juan@rodriguez.org")
        .clave("hunter2")
        .activo(true)
        .creado(fecha)
        .modificado(fecha)
        .ultimoLogin(fecha)
        .telefono(TelefonoFixture.tresTelefonos())
        .token("ABC123")
        .build();
  }

  public static Usuario usuarioSinTelefonoSinId(OffsetDateTime fecha) {
    return Usuario.builder()
        .nombre("Juan")
        .correo("juan@rodriguez.org")
        .clave("hunter2")
        .activo(true)
        .creado(fecha)
        .modificado(fecha)
        .ultimoLogin(fecha)
        .telefono(TelefonoFixture.sinTelefono())
        .token("ABC123")
        .build();
  }
}
