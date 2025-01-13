package nacho.gallo.techforbchallenge.services;

import nacho.gallo.techforbchallenge.models.RevokedToken;
import org.springframework.stereotype.Service;

@Service
public interface RevokedTokensService {
  Boolean existsById(String token);
  void revoke(RevokedToken revokedToken);

}
