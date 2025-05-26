package cl.nttdata.evaluacion.service.impl;

import cl.nttdata.evaluacion.model.Usuario;
import cl.nttdata.evaluacion.repository.UsuarioRepository;
import cl.nttdata.evaluacion.service.MyUserDetailsService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsServiceImpl implements MyUserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findByCorreo(correo);
    return new User(
        usuario.getCorreo(),
        "{noop}" + usuario.getClave(),
        Collections.emptyList()
    );
  }
}
