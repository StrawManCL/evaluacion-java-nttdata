package cl.nttdata.evaluacion.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.nttdata.evaluacion.fixtures.UsuarioFixture;
import cl.nttdata.evaluacion.model.Usuario;
import cl.nttdata.evaluacion.repository.UsuarioRepository;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MyUserDetailsServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class MyUserDetailsServiceImplTest {

  @Autowired
  private MyUserDetailsServiceImpl myUserDetailsServiceImpl;

  @MockitoBean
  private UsuarioRepository usuarioRepository;

  @Test
  void testLoadUserByUsername() throws UsernameNotFoundException {
    OffsetDateTime fecha = OffsetDateTime.now(ZoneOffset.UTC);
    UUID id = UUID.randomUUID();

    Usuario usuario = UsuarioFixture.usuarioConUnTelefono(id, fecha);
    when(usuarioRepository.findByCorreo(Mockito.any())).thenReturn(usuario);

    UserDetails actualLoadUserByUsernameResult = myUserDetailsServiceImpl.loadUserByUsername(
        "juan@rodriguez.org");

    verify(usuarioRepository).findByCorreo(eq("juan@rodriguez.org"));

    Collection<? extends GrantedAuthority> authorities = actualLoadUserByUsernameResult.getAuthorities();
    assertInstanceOf(Set.class, authorities);
    assertInstanceOf(org.springframework.security.core.userdetails.User.class,
        actualLoadUserByUsernameResult);
    assertEquals("juan@rodriguez.org", actualLoadUserByUsernameResult.getUsername());
    assertEquals("{noop}hunter2", actualLoadUserByUsernameResult.getPassword());
    assertTrue(authorities.isEmpty());
    assertTrue(actualLoadUserByUsernameResult.isAccountNonExpired());
    assertTrue(actualLoadUserByUsernameResult.isAccountNonLocked());
    assertTrue(actualLoadUserByUsernameResult.isCredentialsNonExpired());
    assertTrue(actualLoadUserByUsernameResult.isEnabled());
  }
}
