package nacho.gallo.techforbchallenge.services;

import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface JwtService {
  String generateToken(SignUserDTO validateUser);

  String extractUsername(String jwtToken);

  Boolean validateToken(String jwtToken, UserDetails user);
}
