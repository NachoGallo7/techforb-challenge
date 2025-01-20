package nacho.gallo.techforbchallenge.controllers;

import nacho.gallo.techforbchallenge.clients.CountryClient;
import nacho.gallo.techforbchallenge.config.TestSecurityConfig;
import nacho.gallo.techforbchallenge.dtos.country.CountryDTO;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CountryController.class)
@Import(TestSecurityConfig.class)
class CountryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CountryClient countryClient;
  @MockitoBean
  private JwtService jwtService;
  @MockitoBean
  private UserService userService;

  private CountryDTO expectedCountry;
  private List<CountryDTO> expectedCountryList;

  @BeforeEach
  void setUp() {
    expectedCountry = new CountryDTO("https://flagcdn.com/ar.svg", "Argentina", "AR");
    expectedCountryList = List.of(expectedCountry);
  }

  @Test
  @WithMockUser
  void getAll() throws Exception {
    Mockito.when(countryClient.getAll()).thenReturn(expectedCountryList);

    mockMvc.perform(get("/countries")
            .header("Authorization", "Bearer abcdefg")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].flag_icon_url").value(expectedCountryList.get(0).getFlagIconUrl()))
        .andExpect(jsonPath("$[0].name").value(expectedCountryList.get(0).getName()))
        .andExpect(jsonPath("$[0].code").value(expectedCountryList.get(0).getCode()));
  }
}