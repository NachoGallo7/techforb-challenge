package nacho.gallo.techforbchallenge.services.impl;

import nacho.gallo.techforbchallenge.dtos.user.RegisterUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.TokenDTO;
import nacho.gallo.techforbchallenge.dtos.user.UserDTO;
import nacho.gallo.techforbchallenge.entities.UserEntity;
import nacho.gallo.techforbchallenge.models.User;
import nacho.gallo.techforbchallenge.repositories.UserJpaRepository;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

  private final UserJpaRepository userRepository;
  private final ModelMapper modelMapper;
  private final BCryptPasswordEncoder bCryptEncoder;
  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;

  @Autowired
  public UserServiceImpl(UserJpaRepository userRepository, ModelMapper modelMapper,
                         BCryptPasswordEncoder bCryptEncoder, @Lazy AuthenticationManager authenticationManager, JwtService jwtService) {
    this.userRepository = userRepository;
    this.modelMapper = modelMapper;
    this.bCryptEncoder = bCryptEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtService = jwtService;
  }

  @Override
  public UserDTO register(RegisterUserDTO newUser) {
    UserEntity userEntity = modelMapper.map(newUser, UserEntity.class);
    userEntity.setPassword(bCryptEncoder.encode(userEntity.getPassword()));
    userEntity.setCreationDate(LocalDateTime.now());
    userEntity.setUpdateDate(LocalDateTime.now());
    userRepository.saveAndFlush(userEntity);
    return modelMapper.map(userEntity, UserDTO.class);
  }

  @Override
  public TokenDTO login(SignUserDTO validateUser) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(validateUser.getEmail(), validateUser.getPassword()));
    if(!authentication.isAuthenticated()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect credentials");
    }

    return TokenDTO.builder()
        .token(jwtService.generateToken(validateUser))
        .build();
  }

  @Override
  public User findByEmail(String email) {
    UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() ->
        new ResponseStatusException(HttpStatus.NOT_FOUND, "User `" + email + "` not found"));
    return modelMapper.map(userEntity, User.class);
  }
}
