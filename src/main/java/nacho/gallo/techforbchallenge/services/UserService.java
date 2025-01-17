package nacho.gallo.techforbchallenge.services;

import nacho.gallo.techforbchallenge.dtos.user.RegisterUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.TokenDTO;
import nacho.gallo.techforbchallenge.dtos.user.UserDTO;
import nacho.gallo.techforbchallenge.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  UserDTO register(RegisterUserDTO newUser);

  TokenDTO login(SignUserDTO validateUser);

  User findByEmail(String email);
}
