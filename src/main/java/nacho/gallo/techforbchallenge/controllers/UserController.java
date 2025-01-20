package nacho.gallo.techforbchallenge.controllers;

import jakarta.validation.Valid;
import nacho.gallo.techforbchallenge.dtos.user.RegisterUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.TokenDTO;
import nacho.gallo.techforbchallenge.dtos.user.UserDTO;
import nacho.gallo.techforbchallenge.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("register")
  public ResponseEntity<UserDTO> register(@RequestBody @Valid RegisterUserDTO newUser) {
    return ResponseEntity.ok(userService.register(newUser));
  }

  @PostMapping("login")
  public ResponseEntity<TokenDTO> login(@RequestBody @Valid SignUserDTO validateUser) {
    return ResponseEntity.ok(userService.login(validateUser));
  }

}
