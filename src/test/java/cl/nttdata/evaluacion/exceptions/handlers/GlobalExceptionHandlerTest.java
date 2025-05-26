package cl.nttdata.evaluacion.exceptions.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.nttdata.evaluacion.exceptions.EmailAlreadyExistsException;
import cl.nttdata.evaluacion.exceptions.InvalidTokenException;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {

  @Autowired
  private GlobalExceptionHandler globalExceptionHandler;

  @Test
  void testHandleEmailAlreadyExistsException() {
    ResponseEntity<Map<String, String>> actualHandleEmailAlreadyExistsExceptionResult = globalExceptionHandler.handleEmailAlreadyExistsException(
        new EmailAlreadyExistsException("An error occurred"));

    HttpStatusCode statusCode = actualHandleEmailAlreadyExistsExceptionResult.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    Map<String, String> body = actualHandleEmailAlreadyExistsExceptionResult.getBody();
    assertNotNull(body);
    assertEquals(1, body.size());
    assertEquals("An error occurred", body.get("mensaje"));
    assertEquals(409, actualHandleEmailAlreadyExistsExceptionResult.getStatusCode()
        .value());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertTrue(actualHandleEmailAlreadyExistsExceptionResult.hasBody());
    assertTrue(actualHandleEmailAlreadyExistsExceptionResult.getHeaders()
        .isEmpty());
  }

  @Test
  void testHandleEmailAlreadyExistsException2() {
    EmailAlreadyExistsException ex = mock(EmailAlreadyExistsException.class);
    when(ex.getMessage()).thenReturn("Not all who wander are lost");

    ResponseEntity<Map<String, String>> actualHandleEmailAlreadyExistsExceptionResult = globalExceptionHandler.handleEmailAlreadyExistsException(
        ex);

    verify(ex).getMessage();
    HttpStatusCode statusCode = actualHandleEmailAlreadyExistsExceptionResult.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    Map<String, String> body = actualHandleEmailAlreadyExistsExceptionResult.getBody();
    assertNotNull(body);
    assertEquals(1, body.size());
    assertEquals("Not all who wander are lost", body.get("mensaje"));
    assertEquals(409, actualHandleEmailAlreadyExistsExceptionResult.getStatusCode()
        .value());
    assertEquals(HttpStatus.CONFLICT, statusCode);
    assertTrue(actualHandleEmailAlreadyExistsExceptionResult.hasBody());
    assertTrue(actualHandleEmailAlreadyExistsExceptionResult.getHeaders()
        .isEmpty());
  }

  @Test
  void testHandleInvalidTokenException() {
    ResponseEntity<Map<String, String>> actualHandleInvalidTokenExceptionResult = globalExceptionHandler.handleInvalidTokenException(
        new InvalidTokenException("An error occurred"));

    HttpStatusCode statusCode = actualHandleInvalidTokenExceptionResult.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    Map<String, String> body = actualHandleInvalidTokenExceptionResult.getBody();
    assertNotNull(body);
    assertEquals(1, body.size());
    assertEquals("An error occurred", body.get("mensaje"));
    assertEquals(403, actualHandleInvalidTokenExceptionResult.getStatusCode()
        .value());
    assertEquals(HttpStatus.FORBIDDEN, statusCode);
    assertTrue(actualHandleInvalidTokenExceptionResult.hasBody());
    assertTrue(actualHandleInvalidTokenExceptionResult.getHeaders()
        .isEmpty());
  }

  @Test
  void testHandleInvalidTokenException2() {
    InvalidTokenException ex = mock(InvalidTokenException.class);
    when(ex.getMessage()).thenReturn("Not all who wander are lost");

    ResponseEntity<Map<String, String>> actualHandleInvalidTokenExceptionResult = globalExceptionHandler.handleInvalidTokenException(
        ex);

    verify(ex).getMessage();
    HttpStatusCode statusCode = actualHandleInvalidTokenExceptionResult.getStatusCode();
    assertInstanceOf(HttpStatus.class, statusCode);
    Map<String, String> body = actualHandleInvalidTokenExceptionResult.getBody();
    assertNotNull(body);
    assertEquals(1, body.size());
    assertEquals("Not all who wander are lost", body.get("mensaje"));
    assertEquals(403, actualHandleInvalidTokenExceptionResult.getStatusCode()
        .value());
    assertEquals(HttpStatus.FORBIDDEN, statusCode);
    assertTrue(actualHandleInvalidTokenExceptionResult.hasBody());
    assertTrue(actualHandleInvalidTokenExceptionResult.getHeaders()
        .isEmpty());
  }
}
