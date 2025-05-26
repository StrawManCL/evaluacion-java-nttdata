package cl.nttdata.evaluacion.config;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cl.nttdata.evaluacion.exceptions.InvalidTokenException;
import cl.nttdata.evaluacion.service.MyUserDetailsService;
import cl.nttdata.evaluacion.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.catalina.connector.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = {JwtAuthFilter.class})
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class JwtAuthFilterTest {

  @Autowired
  private JwtAuthFilter jwtAuthFilter;

  @MockitoBean
  private JwtUtil jwtUtil;

  @MockitoBean
  private MyUserDetailsService myUserDetailsService;

  @Test
  void testDoFilterInternal() throws ServletException, IOException {
    MockHttpServletRequest request = new MockHttpServletRequest();
    Response response = new Response();
    FilterChain filterChain = mock(FilterChain.class);
    doNothing().when(filterChain)
        .doFilter(Mockito.any(), Mockito.any());

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
  }

  @Test
  void testDoFilterInternal2() throws ServletException, IOException {
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.any())).thenReturn("https://example.org/example");
    Response response = new Response();
    FilterChain filterChain = mock(FilterChain.class);
    doNothing().when(filterChain)
        .doFilter(Mockito.any(), Mockito.any());

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).getHeader(eq("Authorization"));
  }

  @Test
  void testDoFilterInternal3()
      throws InvalidTokenException, ServletException, IOException, UsernameNotFoundException {
    when(myUserDetailsService.loadUserByUsername(Mockito.any())).thenReturn(
        new User("janedoe", "iloveyou", new ArrayList<>()));
    when(jwtUtil.validateToken(Mockito.any(), Mockito.any())).thenReturn(true);
    when(jwtUtil.extractUsername(Mockito.any())).thenReturn("janedoe");
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getSession(anyBoolean())).thenReturn(new MockHttpSession());
    when(request.getRemoteAddr()).thenReturn("42 Main St");
    when(request.getHeader(Mockito.any())).thenReturn("Bearer ");
    Response response = new Response();
    FilterChain filterChain = mock(FilterChain.class);
    doNothing().when(filterChain)
        .doFilter(Mockito.any(), Mockito.any());

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(jwtUtil).extractUsername(eq(""));
    verify(jwtUtil).validateToken(eq(""), isA(UserDetails.class));
    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).getRemoteAddr();
    verify(request).getHeader(eq("Authorization"));
    verify(request).getSession(eq(false));
    verify(myUserDetailsService).loadUserByUsername(eq("janedoe"));
  }

  @Test
  void testDoFilterInternal4()
      throws InvalidTokenException, ServletException, IOException, UsernameNotFoundException {
    when(myUserDetailsService.loadUserByUsername(Mockito.any())).thenReturn(
        new User("janedoe", "iloveyou", new ArrayList<>()));
    when(jwtUtil.validateToken(Mockito.any(), Mockito.any())).thenReturn(false);
    when(jwtUtil.extractUsername(Mockito.any())).thenReturn("janedoe");
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.any())).thenReturn("Bearer ");
    Response response = new Response();
    FilterChain filterChain = mock(FilterChain.class);
    doNothing().when(filterChain)
        .doFilter(Mockito.any(), Mockito.any());

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(jwtUtil).extractUsername(eq(""));
    verify(jwtUtil).validateToken(eq(""), isA(UserDetails.class));
    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).getHeader(eq("Authorization"));
    verify(myUserDetailsService).loadUserByUsername(eq("janedoe"));
  }

  @Test
  void testDoFilterInternal5() throws InvalidTokenException, ServletException, IOException {
    when(jwtUtil.extractUsername(Mockito.any())).thenReturn(null);
    HttpServletRequestWrapper request = mock(HttpServletRequestWrapper.class);
    when(request.getHeader(Mockito.any())).thenReturn("Bearer ");
    Response response = new Response();
    FilterChain filterChain = mock(FilterChain.class);
    doNothing().when(filterChain)
        .doFilter(Mockito.any(), Mockito.any());

    jwtAuthFilter.doFilterInternal(request, response, filterChain);

    verify(jwtUtil).extractUsername(eq(""));
    verify(filterChain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
    verify(request).getHeader(eq("Authorization"));
  }
}
