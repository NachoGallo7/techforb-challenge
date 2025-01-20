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
import nacho.gallo.techforbchallenge.services.PlantService;
import nacho.gallo.techforbchallenge.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PlantServiceImpl implements PlantService {

  private final PlantJpaRepository plantRepository;
  private final ModelMapper modelMapper;
  private final JwtService jwtService;
  private final UserService userService;

  @Autowired
  public PlantServiceImpl(PlantJpaRepository plantRepository, ModelMapper modelMapper, JwtService jwtService, UserService userService) {
    this.plantRepository = plantRepository;
    this.modelMapper = modelMapper;
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  public Page<PlantDTO> getAll(Pageable pageable, String authToken) {
    Page<PlantEntity> plants = plantRepository.findAllByUser_Id(getUserFromToken(authToken).getId(), pageable);
    return plants.map(plant -> modelMapper.map(plant, PlantDTO.class));
  }

  @Override
  public PlantDTO getById(Long id, String authToken) {
    PlantEntity plantEntity = findEntityByIdAndUserId(id, getUserFromToken(authToken).getId());
    return modelMapper.map(plantEntity, PlantDTO.class);
  }
  @Override
  @Transactional
  public PlantDTO create(PostPlantDTO newPlant, String authToken) {
    newPlant.setCountryCode(newPlant.getCountryCode().toLowerCase());
    UserEntity userEntity = UserEntity.builder().id(getUserFromToken(authToken).getId()).build();
    PlantEntity toSavePlant = modelMapper.map(newPlant, PlantEntity.class);
    toSavePlant.setCreationDate(LocalDateTime.now());
    toSavePlant.setUpdateDate(LocalDateTime.now());
    toSavePlant.setIsActive(true);
    toSavePlant.setUser(userEntity);

    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.TEMPERATURE));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.PRESSURE));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.WIND));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.LEVELS));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.ENERGY));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.TENSION));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.CARBON_MONOXIDE));
    toSavePlant.getPlantDetails().add(new PlantDetailEntity(toSavePlant, PlantDetailType.OTHER_GASSES));

    PlantEntity savedPlant = plantRepository.saveAndFlush(toSavePlant);
    return modelMapper.map(savedPlant, PlantDTO.class);
  }

  @Override
  public PlantDTO update(Long id, PutPlantDTO updatedPlant, String authToken) {
    PlantEntity plantEntity = findEntityByIdAndUserId(id, getUserFromToken(authToken).getId());

    plantEntity.setReadings(updatedPlant.getReadings());
    plantEntity.setWarnings(updatedPlant.getWarnings());
    plantEntity.setAlerts(updatedPlant.getAlerts());
    plantEntity.setDisabledSensors(updatedPlant.getDisabledSensors());
    plantEntity.setUpdateDate(LocalDateTime.now());

    PlantDetailEntity plantDetailEntity = plantEntity.getPlantDetails().stream()
        .filter(detail -> detail.getPlantDetailType().equals(PlantDetailType.TEMPERATURE))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant detail not found"));
    plantDetailEntity.setReadings(updatedPlant.getReadings());
    plantDetailEntity.setWarnings(updatedPlant.getWarnings());
    plantDetailEntity.setAlerts(updatedPlant.getAlerts());
    plantDetailEntity.setDisabledSensors(updatedPlant.getDisabledSensors());

    plantRepository.saveAndFlush(plantEntity);
    return modelMapper.map(plantEntity, PlantDTO.class);
  }

  @Override
  public void delete(Long id, String authToken) {
    PlantEntity plantEntity = findEntityByIdAndUserId(id, getUserFromToken(authToken).getId());
    if (!plantEntity.getIsActive()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "The plant was already removed");
    }
    plantEntity.setIsActive(false);
    plantRepository.saveAndFlush(plantEntity);
  }

  private PlantEntity findEntityByIdAndUserId(Long id, Long userId) {
    return plantRepository.findByIdAndUser_Id(id, userId)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found"));
  }

  private User getUserFromToken(String authToken) {
//    String userEmail = "igna@gmail.com";
    String userEmail = jwtService.extractSubject(authToken.substring(7));
    return userService.findByEmail(userEmail);
  }

}
