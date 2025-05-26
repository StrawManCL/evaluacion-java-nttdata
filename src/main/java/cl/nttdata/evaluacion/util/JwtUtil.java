package cl.nttdata.evaluacion.util;

import cl.nttdata.evaluacion.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";
  private static final String CLAIM_ISSUER = "nttdata";
  private static final long ACCESS_TOKEN_VALIDITY_HOURS = 24;
  private static final long ACCESS_TOKEN_VALIDITY_MS = ACCESS_TOKEN_VALIDITY_HOURS * 60 * 60 * 1000;
  private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

  public String generateToken(String user) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, user);
  }

  private String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
        .claims()
        .issuer(CLAIM_ISSUER)
        .subject(username)
        .add(claims)
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_MS))
        .and()
        .signWith(SECRET_KEY)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails user) throws InvalidTokenException {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) throws ExpiredJwtException {
    return extractClaim(token, Claims::getExpiration);
  }

  public String extractUsername(String token) throws InvalidTokenException {
    return extractClaim(token, Claims::getSubject);
  }

  private Claims extractAllClaims(String token) {
    try {
      return Jwts.parser()
          .verifyWith(SECRET_KEY)
          .build()
          .parseSignedClaims(token)
          .getPayload();
    } catch (MalformedJwtException | SignatureException e) {
      throw new InvalidTokenException(e.getMessage());
    }
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    try {
      final Claims claims = extractAllClaims(token);
      return claimsResolver.apply(claims);
    } catch (InvalidTokenException e) {
      log.error(e.getMessage());
    }
    return null;
  }
}