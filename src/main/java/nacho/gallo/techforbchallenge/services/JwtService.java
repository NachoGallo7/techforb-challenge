package nacho.gallo.techforbchallenge.services;

import io.jsonwebtoken.Claims;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.models.User;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
  String generateToken(SignUserDTO validateUser);

  String extractSubject(String jwtToken);

  Boolean validateToken(String jwtToken, User user);

  Claims getTokenClaims(String jwtToken);
}
