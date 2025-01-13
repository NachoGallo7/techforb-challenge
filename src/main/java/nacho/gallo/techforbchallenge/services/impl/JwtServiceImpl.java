package nacho.gallo.techforbchallenge.services.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.models.User;
import nacho.gallo.techforbchallenge.repositories.RevokedTokensJpaRepository;
import nacho.gallo.techforbchallenge.services.JwtService;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Service
public class JwtServiceImpl implements JwtService {

  private SecretKey secretKey;
  private final RevokedTokensJpaRepository revokedTokensRepository;

  public JwtServiceImpl(RevokedTokensJpaRepository revokedTokensRepository) throws NoSuchAlgorithmException {
    this.revokedTokensRepository = revokedTokensRepository;
    this.secretKey = KeyGenerator.getInstance("HmacSHA256").generateKey();
  }

  @Override
  public String generateToken(SignUserDTO validateUser) {
    return Jwts.builder()
        .claims()
        .issuer("Techforb")
        .subject(validateUser.getEmail())
        .issuedAt(Date.from(Instant.now()))
        .expiration(Date.from(Instant.now().plus(10, ChronoUnit.DAYS)))
        .and()
        .signWith(secretKey)
        .compact();
  }

  @Override
  public String extractUsername(String jwtToken) {
    Claims claims = getTokenClaims(jwtToken);
    return claims.getSubject();
  }

  @Override
  public Boolean validateToken(String jwtToken, User user) {
    Claims claims = getTokenClaims(jwtToken);
    boolean isTokenRevoked = revokedTokensRepository.existsById(jwtToken);

    return !isTokenRevoked
        && claims.getSubject().equals(user.getUsername())
        && claims.getExpiration().after(Date.from(Instant.now()))
        && (Objects.isNull(claims.getNotBefore()) || claims.getNotBefore().before(Date.from(Instant.now())));
  }

  @Override
  public Claims getTokenClaims(String jwtToken) {
    return Jwts.parser()
        .verifyWith(secretKey)
        .build()
        .parseSignedClaims(jwtToken)
        .getPayload();
  }
}
