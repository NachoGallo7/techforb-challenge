package nacho.gallo.techforbchallenge.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nacho.gallo.techforbchallenge.config.TestSecurityConfig;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.user.RegisterUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.SignUserDTO;
import nacho.gallo.techforbchallenge.dtos.user.TokenDTO;
import nacho.gallo.techforbchallenge.dtos.user.UserDTO;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(TestSecurityConfig.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private UserService userService;
  @MockitoBean
  private JwtService jwtService;

  private RegisterUserDTO expectedRegisterUser;
  private SignUserDTO expectedSignUserDTO;
  private UserDTO expectedUserDTO;
  private TokenDTO expectedTokenDTO;

  @BeforeEach
  void setUp() {
    expectedRegisterUser = new RegisterUserDTO("username", "email@email.com", "123456789");
    expectedSignUserDTO = new SignUserDTO("email@email.com", "123456789");
    expectedUserDTO = new UserDTO(1L, "email@email.com", "123456789");
    expectedTokenDTO = new TokenDTO("abdefg");
  }

  @Test
  @WithMockUser
  void register() throws Exception {
    Mockito.when(userService.register(any(RegisterUserDTO.class))).thenReturn(expectedUserDTO);

    mockMvc.perform(post("/users/register")
            .content(objectMapper.writeValueAsString(expectedRegisterUser))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedUserDTO.getId()))
        .andExpect(jsonPath("$.email").value(expectedUserDTO.getEmail()))
        .andExpect(jsonPath("$.username").value(expectedUserDTO.getUsername()));
  }

  @Test
  @WithMockUser
  void login() throws Exception {
    Mockito.when(userService.login(any(SignUserDTO.class))).thenReturn(expectedTokenDTO);

    mockMvc.perform(post("/users/login")
            .content(objectMapper.writeValueAsString(expectedSignUserDTO))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value(expectedTokenDTO.getToken()));
  }
}