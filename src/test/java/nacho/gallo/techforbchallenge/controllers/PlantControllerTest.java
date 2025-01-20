package nacho.gallo.techforbchallenge.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nacho.gallo.techforbchallenge.config.TestSecurityConfig;
import nacho.gallo.techforbchallenge.dtos.plant.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PutPlantDTO;
import nacho.gallo.techforbchallenge.models.PlantDetail;
import nacho.gallo.techforbchallenge.models.PlantDetailType;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.PlantService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlantController.class)
@Import(TestSecurityConfig.class)
class PlantControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private PlantService plantService;
  @MockitoBean
  private JwtService jwtService;
  @MockitoBean
  private UserService userService;

  private PlantDTO expectedPlant;
  private List<PlantDTO> expectedPlantList;

  @BeforeEach
  void setUp() {
    Set<PlantDetail> expectedPlantDetailList = new HashSet<>();

    PlantDetail temperature = new PlantDetail();
    temperature.setId(1L);
    temperature.setReadings(100);
    temperature.setWarnings(50);
    temperature.setAlerts(25);
    temperature.setDisabledSensors(2);
    temperature.setPlantDetailType(PlantDetailType.TEMPERATURE);
    expectedPlantDetailList.add(temperature);

    PlantDetail pressure = new PlantDetail();
    pressure.setId(2L);
    pressure.setPlantDetailType(PlantDetailType.PRESSURE);
    expectedPlantDetailList.add(pressure);

    PlantDetail wind = new PlantDetail();
    wind.setId(3L);
    wind.setPlantDetailType(PlantDetailType.WIND);
    expectedPlantDetailList.add(wind);

    PlantDetail levels = new PlantDetail();
    levels.setId(4L);
    levels.setPlantDetailType(PlantDetailType.LEVELS);
    expectedPlantDetailList.add(levels);

    PlantDetail energy = new PlantDetail();
    energy.setId(5L);
    energy.setPlantDetailType(PlantDetailType.ENERGY);
    expectedPlantDetailList.add(energy);

    PlantDetail tension = new PlantDetail();
    tension.setId(6L);
    tension.setPlantDetailType(PlantDetailType.TENSION);
    expectedPlantDetailList.add(tension);

    PlantDetail carbonMonoxide = new PlantDetail();
    carbonMonoxide.setId(7L);
    carbonMonoxide.setPlantDetailType(PlantDetailType.CARBON_MONOXIDE);
    expectedPlantDetailList.add(carbonMonoxide);

    PlantDetail otherGasses = new PlantDetail();
    otherGasses.setId(8L);
    otherGasses.setPlantDetailType(PlantDetailType.OTHER_GASSES);
    expectedPlantDetailList.add(otherGasses);

    expectedPlant = new PlantDTO(1L, "Quilmes", "Argentina", "ar", 100, 50, 25, 2, true, expectedPlantDetailList);
    expectedPlantList = List.of(expectedPlant);
  }

  @Test
  @WithMockUser
  void getAll() throws Exception {
    Mockito.when(plantService.getAll(any(Pageable.class), anyString())).thenReturn(new PageImpl<>(expectedPlantList));

    mockMvc.perform(get("/plants")
            .header("Authorization", "Bearer abcdefg")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.content[0].id").value(expectedPlantList.get(0).getId()))
        .andExpect(jsonPath("$.content[0].name").value(expectedPlantList.get(0).getName()))
        .andExpect(jsonPath("$.content[0].country").value(expectedPlantList.get(0).getCountry()))
        .andExpect(jsonPath("$.content[0].country_code").value(expectedPlantList.get(0).getCountryCode()))
        .andExpect(jsonPath("$.content[0].readings").value(expectedPlantList.get(0).getReadings()))
        .andExpect(jsonPath("$.content[0].warnings").value(expectedPlantList.get(0).getWarnings()))
        .andExpect(jsonPath("$.content[0].alerts").value(expectedPlantList.get(0).getAlerts()))
        .andExpect(jsonPath("$.content[0].disabled_sensors").value(expectedPlantList.get(0).getDisabledSensors()))
        .andExpect(jsonPath("$.content[0].is_active").value(expectedPlantList.get(0).getIsActive()));
  }

  @Test
  @WithMockUser
  void getById() throws Exception {
    Mockito.when(plantService.getById(anyLong(), anyString())).thenReturn(expectedPlant);

    mockMvc.perform(get("/plants/1")
            .header("Authorization", "Bearer abcdefg")
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedPlant.getId()))
        .andExpect(jsonPath("$.name").value(expectedPlant.getName()))
        .andExpect(jsonPath("$.country").value(expectedPlant.getCountry()))
        .andExpect(jsonPath("$.country_code").value(expectedPlant.getCountryCode()))
        .andExpect(jsonPath("$.readings").value(expectedPlant.getReadings()))
        .andExpect(jsonPath("$.warnings").value(expectedPlant.getWarnings()))
        .andExpect(jsonPath("$.alerts").value(expectedPlant.getAlerts()))
        .andExpect(jsonPath("$.disabled_sensors").value(expectedPlant.getDisabledSensors()))
        .andExpect(jsonPath("$.is_active").value(expectedPlant.getIsActive()));
  }

  @Test
  @WithMockUser
  void create() throws Exception {
    Mockito.when(plantService.create(any(PostPlantDTO.class), anyString())).thenReturn(expectedPlant);

    mockMvc.perform(post("/plants")
            .header("Authorization", "Bearer abcdefg")
            .content(objectMapper.writeValueAsString(expectedPlant))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedPlant.getId()))
        .andExpect(jsonPath("$.name").value(expectedPlant.getName()))
        .andExpect(jsonPath("$.country").value(expectedPlant.getCountry()))
        .andExpect(jsonPath("$.country_code").value(expectedPlant.getCountryCode()))
        .andExpect(jsonPath("$.readings").value(expectedPlant.getReadings()))
        .andExpect(jsonPath("$.warnings").value(expectedPlant.getWarnings()))
        .andExpect(jsonPath("$.alerts").value(expectedPlant.getAlerts()))
        .andExpect(jsonPath("$.disabled_sensors").value(expectedPlant.getDisabledSensors()))
        .andExpect(jsonPath("$.is_active").value(expectedPlant.getIsActive()));
  }

  @Test
  @WithMockUser
  void update() throws Exception {
    Mockito.when(plantService.update(anyLong(), any(PutPlantDTO.class), anyString())).thenReturn(expectedPlant);

    mockMvc.perform(put("/plants/1")
            .header("Authorization", "Bearer abcdefg")
            .content(objectMapper.writeValueAsString(expectedPlant))
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(expectedPlant.getId()))
        .andExpect(jsonPath("$.name").value(expectedPlant.getName()))
        .andExpect(jsonPath("$.country").value(expectedPlant.getCountry()))
        .andExpect(jsonPath("$.country_code").value(expectedPlant.getCountryCode()))
        .andExpect(jsonPath("$.readings").value(expectedPlant.getReadings()))
        .andExpect(jsonPath("$.warnings").value(expectedPlant.getWarnings()))
        .andExpect(jsonPath("$.alerts").value(expectedPlant.getAlerts()))
        .andExpect(jsonPath("$.disabled_sensors").value(expectedPlant.getDisabledSensors()))
        .andExpect(jsonPath("$.is_active").value(expectedPlant.getIsActive()));
  }

  @Test
  @WithMockUser
  void delete() throws Exception {
    doNothing().when(plantService).delete(anyLong(), anyString());

    mockMvc.perform(MockMvcRequestBuilders.delete("/plants/1")
            .header("Authorization", "Bearer abcdefg")
        )
        .andExpect(status().isNoContent());
  }
}