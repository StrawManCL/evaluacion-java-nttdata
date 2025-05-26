package cl.nttdata.evaluacion.config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@ContextConfiguration(classes = {CorsConfig.class})
@ExtendWith(SpringExtension.class)
class CorsConfigTest {

  @Autowired
  private CorsConfig corsConfig;

  @Test
  void testAddCorsMappings() {
    CorsRegistry registry = mock(CorsRegistry.class);
    when(registry.addMapping(Mockito.any())).thenReturn(new CorsRegistration("Path Pattern"));

    corsConfig.addCorsMappings(registry);

    verify(registry).addMapping(eq("/**"));
  }
}
