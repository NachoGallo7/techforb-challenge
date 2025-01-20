package nacho.gallo.techforbchallenge.services.impl;

import nacho.gallo.techforbchallenge.dtos.plant.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PutPlantDTO;
import nacho.gallo.techforbchallenge.entities.PlantDetailEntity;
import nacho.gallo.techforbchallenge.entities.PlantEntity;
import nacho.gallo.techforbchallenge.entities.UserEntity;
import nacho.gallo.techforbchallenge.models.PlantDetail;
import nacho.gallo.techforbchallenge.models.PlantDetailType;
import nacho.gallo.techforbchallenge.models.User;
import nacho.gallo.techforbchallenge.repositories.PlantJpaRepository;
import nacho.gallo.techforbchallenge.services.JwtService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class PlantServiceImplTest {

  @Mock
  private PlantJpaRepository plantRepository;
  @Mock
  private ModelMapper modelMapper;
  @Mock
  private JwtService jwtService;
  @Mock
  private UserService userService;

  @InjectMocks
  private PlantServiceImpl plantService;

  private PlantDTO expectedPlant;
  private List<PlantDTO> expectedPlantList;
  private PlantEntity expectedPlantEntity;
  private UserEntity expectedUserEntity;
  private Set<PlantDetailEntity> expectedPlantDetailEntityList;
  private List<PlantEntity> expectedPlantEntityList;
  private User expectedUser;
  private PostPlantDTO expectedPostPlantDTO;
  private PutPlantDTO expectedPutPlantDTO;

  @BeforeEach
  void setUp() {
    //Models
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

    //Entities
    expectedPlantDetailEntityList = new HashSet<>();

    PlantDetailEntity temperatureEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.TEMPERATURE);
    temperatureEntity.setId(1L);
    temperatureEntity.setReadings(100);
    temperatureEntity.setWarnings(50);
    temperatureEntity.setAlerts(25);
    temperatureEntity.setDisabledSensors(2);
    temperatureEntity.setPlantDetailType(PlantDetailType.TEMPERATURE);
    expectedPlantDetailEntityList.add(temperatureEntity);

    PlantDetailEntity pressureEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.PRESSURE);
    pressureEntity.setId(2L);
    pressureEntity.setPlantDetailType(PlantDetailType.PRESSURE);
    expectedPlantDetailEntityList.add(pressureEntity);

    PlantDetailEntity windEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.WIND);
    windEntity.setId(3L);
    windEntity.setPlantDetailType(PlantDetailType.WIND);
    expectedPlantDetailEntityList.add(windEntity);

    PlantDetailEntity levelsEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.LEVELS);
    levelsEntity.setId(4L);
    levelsEntity.setPlantDetailType(PlantDetailType.LEVELS);
    expectedPlantDetailEntityList.add(levelsEntity);

    PlantDetailEntity energyEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.ENERGY);
    energyEntity.setId(5L);
    energyEntity.setPlantDetailType(PlantDetailType.ENERGY);
    expectedPlantDetailEntityList.add(energyEntity);

    PlantDetailEntity tensionEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.TENSION);
    tensionEntity.setId(6L);
    tensionEntity.setPlantDetailType(PlantDetailType.TENSION);
    expectedPlantDetailEntityList.add(tensionEntity);

    PlantDetailEntity carbonMonoxideEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.CARBON_MONOXIDE);
    carbonMonoxideEntity.setId(7L);
    carbonMonoxideEntity.setPlantDetailType(PlantDetailType.CARBON_MONOXIDE);
    expectedPlantDetailEntityList.add(carbonMonoxideEntity);

    PlantDetailEntity otherGassesEntity = new PlantDetailEntity(expectedPlantEntity, PlantDetailType.OTHER_GASSES);
    otherGassesEntity.setId(8L);
    otherGassesEntity.setPlantDetailType(PlantDetailType.OTHER_GASSES);
    expectedPlantDetailEntityList.add(otherGassesEntity);

    expectedUserEntity = new UserEntity();
    expectedUserEntity.setId(1L);
    expectedPlantEntity = new PlantEntity(1L, "Quilmes", "Argentina", "ar", 100, 50, 25, 2, true, expectedUserEntity, expectedPlantDetailEntityList);
    expectedPlantEntityList = List.of(expectedPlantEntity);

    expectedUser = new User(1L, "apodo", "email@email.com", "123456789");

    expectedPostPlantDTO = new PostPlantDTO("Quilmes", "Argentina", "ar");
    expectedPutPlantDTO = new PutPlantDTO(100, 50, 25, 2, expectedPlantDetailList);
  }

  @Test
  void getAll() {
    //GIVEN
    when(plantRepository.findAllByUser_Id(anyLong(), any(Pageable.class))).thenReturn(new PageImpl<>(expectedPlantEntityList));
    when(modelMapper.map(any(PlantEntity.class), eq(PlantDTO.class))).thenReturn(expectedPlant);
    when(jwtService.extractSubject(anyString())).thenReturn("email@email.com");
    when(userService.findByEmail(anyString())).thenReturn(expectedUser);

    //WHEN
    Page<PlantDTO> actualResult = plantService.getAll(Pageable.ofSize(10), "abcdefg");

    //THEN
    assertFalse(actualResult.isEmpty());
    assertEquals(expectedPlant.getId(), actualResult.getContent().get(0).getId());
    assertEquals(expectedPlant.getName(), actualResult.getContent().get(0).getName());
    assertEquals(expectedPlant.getCountry(), actualResult.getContent().get(0).getCountry());
    assertEquals(expectedPlant.getCountryCode(), actualResult.getContent().get(0).getCountryCode());
    assertEquals(expectedPlant.getReadings(), actualResult.getContent().get(0).getReadings());
    assertEquals(expectedPlant.getWarnings(), actualResult.getContent().get(0).getWarnings());
    assertEquals(expectedPlant.getAlerts(), actualResult.getContent().get(0).getAlerts());
    assertEquals(expectedPlant.getDisabledSensors(), actualResult.getContent().get(0).getDisabledSensors());
    assertEquals(expectedPlant.getIsActive(), actualResult.getContent().get(0).getIsActive());
  }

  @Test
  void getById() {
    //GIVEN
    when(plantRepository.findByIdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.ofNullable(expectedPlantEntity));
    when(modelMapper.map(any(PlantEntity.class), eq(PlantDTO.class))).thenReturn(expectedPlant);
    when(jwtService.extractSubject(anyString())).thenReturn("email@email.com");
    when(userService.findByEmail(anyString())).thenReturn(expectedUser);

    //WHEN
    PlantDTO actualResult = plantService.getById(1L, "abcdefg");

    //THEN
    assertEquals(expectedPlant.getId(), actualResult.getId());
    assertEquals(expectedPlant.getName(), actualResult.getName());
    assertEquals(expectedPlant.getCountry(), actualResult.getCountry());
    assertEquals(expectedPlant.getCountryCode(), actualResult.getCountryCode());
    assertEquals(expectedPlant.getReadings(), actualResult.getReadings());
    assertEquals(expectedPlant.getWarnings(), actualResult.getWarnings());
    assertEquals(expectedPlant.getAlerts(), actualResult.getAlerts());
    assertEquals(expectedPlant.getDisabledSensors(), actualResult.getDisabledSensors());
    assertEquals(expectedPlant.getIsActive(), actualResult.getIsActive());
  }

  @Test
  void create() {
    //GIVEN
    when(modelMapper.map(any(PlantEntity.class), eq(PlantDTO.class))).thenReturn(expectedPlant);
    when(modelMapper.map(any(PostPlantDTO.class), eq(PlantEntity.class))).thenReturn(expectedPlantEntity);
    when(jwtService.extractSubject(anyString())).thenReturn("email@email.com");
    when(userService.findByEmail(anyString())).thenReturn(expectedUser);
    when(plantRepository.saveAndFlush(any(PlantEntity.class))).thenReturn(expectedPlantEntity);

    //WHEN
    PlantDTO actualResult = plantService.create(expectedPostPlantDTO, "abcdefg");

    //THEN
    assertEquals(expectedPlant.getId(), actualResult.getId());
    assertEquals(expectedPlant.getName(), actualResult.getName());
    assertEquals(expectedPlant.getCountry(), actualResult.getCountry());
    assertEquals(expectedPlant.getCountryCode(), actualResult.getCountryCode());
    assertEquals(expectedPlant.getReadings(), actualResult.getReadings());
    assertEquals(expectedPlant.getWarnings(), actualResult.getWarnings());
    assertEquals(expectedPlant.getAlerts(), actualResult.getAlerts());
    assertEquals(expectedPlant.getDisabledSensors(), actualResult.getDisabledSensors());
    assertEquals(expectedPlant.getIsActive(), actualResult.getIsActive());
  }

  @Test
  void update() {
    //GIVEN
    when(plantRepository.findByIdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.ofNullable(expectedPlantEntity));
    when(modelMapper.map(any(PlantEntity.class), eq(PlantDTO.class))).thenReturn(expectedPlant);
    when(jwtService.extractSubject(anyString())).thenReturn("email@email.com");
    when(userService.findByEmail(anyString())).thenReturn(expectedUser);
    when(plantRepository.saveAndFlush(any(PlantEntity.class))).thenReturn(expectedPlantEntity);

    //WHEN
    PlantDTO actualResult = plantService.update(1L, expectedPutPlantDTO, "abcdefg");

    //THEN
    assertEquals(expectedPlant.getId(), actualResult.getId());
    assertEquals(expectedPlant.getName(), actualResult.getName());
    assertEquals(expectedPlant.getCountry(), actualResult.getCountry());
    assertEquals(expectedPlant.getCountryCode(), actualResult.getCountryCode());
    assertEquals(expectedPlant.getReadings(), actualResult.getReadings());
    assertEquals(expectedPlant.getWarnings(), actualResult.getWarnings());
    assertEquals(expectedPlant.getAlerts(), actualResult.getAlerts());
    assertEquals(expectedPlant.getDisabledSensors(), actualResult.getDisabledSensors());
    assertEquals(expectedPlant.getIsActive(), actualResult.getIsActive());
  }

  @Test
  void delete() {
    //GIVEN
    when(plantRepository.findByIdAndUser_Id(anyLong(), anyLong())).thenReturn(Optional.ofNullable(expectedPlantEntity));
    when(jwtService.extractSubject(anyString())).thenReturn("email@email.com");
    when(userService.findByEmail(anyString())).thenReturn(expectedUser);
    //WHEN && THEN
    assertDoesNotThrow(() -> {
      plantService.delete(1L, "abcdefg");
    });
  }
}