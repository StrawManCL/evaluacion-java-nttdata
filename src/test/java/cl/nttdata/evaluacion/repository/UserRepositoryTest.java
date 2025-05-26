package cl.nttdata.evaluacion.repository;

import static org.assertj.core.api.Assertions.assertThat;

import cl.nttdata.evaluacion.fixtures.UsuarioFixture;
import cl.nttdata.evaluacion.model.Usuario;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@EntityScan(basePackages = {"cl.nttdata.evaluacion.model"})
@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Test
  void testFindByEmail() {
    OffsetDateTime fecha = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);

    Usuario usuario = UsuarioFixture.usuarioSinTelefonoSinId(fecha);
    Usuario usuarioGuardado = usuarioRepository.save(usuario);

    Usuario usuarioEncontrado = usuarioRepository.findByCorreo("juan@rodriguez.org");
    assertThat(usuarioEncontrado).isNotNull();
    assertThat(usuarioEncontrado.getCorreo()).isEqualTo("juan@rodriguez.org");
    assertThat(usuarioEncontrado.getId()).isEqualTo(usuarioGuardado.getId());
  }

  @Test
  void testExistsByEmail() {
    OffsetDateTime fecha = OffsetDateTime.of(LocalDate.of(1970, 1, 1), LocalTime.MIDNIGHT,
        ZoneOffset.UTC);

    Usuario usuario = UsuarioFixture.usuarioSinTelefonoSinId(fecha);

    usuarioRepository.save(usuario);

    boolean encontrado = usuarioRepository.existsByCorreo("juan@rodriguez.org");

    assertThat(encontrado).isTrue();
  }
}
