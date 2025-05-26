package cl.nttdata.evaluacion.repository;

import cl.nttdata.evaluacion.model.Usuario;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

  Usuario findByCorreo(String correo);

  boolean existsByCorreo(String correo);
}
