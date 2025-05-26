package cl.nttdata.evaluacion.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import cl.nttdata.evaluacion.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {JwtUtil.class})
@ExtendWith(SpringExtension.class)
class JwtUtilTest {

  private static final String TOKEN_TEST = "eyJhbGciOiJIUzI1NiJ9"
      + ".eyJpc3MiOiJOZW9yaXMiLCJzdWIiOiJMZW5vcmVfRGFyZTk2QHlhaG9vLmNvbSIsImlhdCI6MTcyNjU3MjU5OCwiZXhwIjoxNzI2NTgzMzk4fQ"
      + ".kzl39h97oMLUUELmauwk_mcLJkEYmrww5v37gU1QbJs";

  @Autowired
  private JwtUtil jwtUtil;

  @Test
  void testGenerateToken() {
    String token = jwtUtil.generateToken("User");

    assertNotNull(token);
  }

  @Test
  void testExtractExpiration() throws ExpiredJwtException {
    assertNull((new JwtUtil()).extractExpiration(TOKEN_TEST));
  }

  @Test
  void testExtractUsername() throws InvalidTokenException {
    assertNull((new JwtUtil()).extractUsername(TOKEN_TEST));
  }

  @Test
  void testExtractClaim() {
    Function<Claims, Object> mockFunction = mock();
    assertNull((new JwtUtil()).extractClaim(TOKEN_TEST, mockFunction));
  }
}
