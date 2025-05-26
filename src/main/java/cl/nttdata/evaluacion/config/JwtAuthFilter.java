package cl.nttdata.evaluacion.config;

import cl.nttdata.evaluacion.service.MyUserDetailsService;
import cl.nttdata.evaluacion.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  private final MyUserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  /**
   * Filtro que intercepta cada solicitud HTTP para verificar la validez del token JWT.
   *
   * <p>Este método extrae el header {@code "Authorization"} del request HTTP, verifica si comienza
   * con el prefijo esperado ({@code "Bearer "}), y en caso afirmativo, intenta extraer el nombre de
   * usuario y validar el token.</p>
   *
   * <p>Si el token es válido y no existe una autenticación previa en el
   * {@link SecurityContextHolder}, se crea un {@link UsernamePasswordAuthenticationToken} con los
   * datos del usuario y se establece en el contexto de seguridad.</p>
   *
   * @param request      solicitud HTTP
   * @param response     respuesta HTTP
   * @param filterChain  cadena de filtros a continuar una vez procesado este filtro
   * @throws ServletException si ocurre un error durante el procesamiento del filtro
   * @throws IOException      si ocurre un error de entrada/salida durante el procesamiento
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authHeader = request.getHeader(JwtUtil.TOKEN_HEADER);

    if (authHeader != null && authHeader.startsWith(JwtUtil.TOKEN_PREFIX)) {
      String token = authHeader.substring(7);
      String userName = jwtUtil.extractUsername(token);

      if (userName != null && SecurityContextHolder.getContext()
          .getAuthentication() == null) {
        UserDetails user = userDetailsService.loadUserByUsername(userName);

        if (jwtUtil.validateToken(token, user)) {
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user,
              null, user.getAuthorities());
          auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext()
              .setAuthentication(auth);
        }
      }
    }
    filterChain.doFilter(request, response);
  }
}