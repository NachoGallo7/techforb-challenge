package nacho.gallo.techforbchallenge.services.impl;

import nacho.gallo.techforbchallenge.dtos.plant.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PutPlantDTO;
import nacho.gallo.techforbchallenge.entities.PlantEntity;
import nacho.gallo.techforbchallenge.repositories.PlantJpaRepository;
import nacho.gallo.techforbchallenge.services.PlantService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PlantServiceImpl implements PlantService {

  private final PlantJpaRepository plantRepository;
  private final ModelMapper modelMapper;

  @Autowired
  public PlantServiceImpl(PlantJpaRepository plantRepository, ModelMapper modelMapper) {
    this.plantRepository = plantRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public Page<PlantDTO> getAll(Pageable pageable) {
    Page<PlantEntity> plants = plantRepository.findAll(pageable);
    return plants.map(plant -> modelMapper.map(plant, PlantDTO.class));
  }

  @Override
  public PlantDTO getById(Long id) {
    PlantEntity plantEntity = findEntityById(id);
    return modelMapper.map(plantEntity, PlantDTO.class);
  }
  @Override
  public PlantDTO create(PostPlantDTO newPlant) {
    PlantEntity toSavePlant = modelMapper.map(newPlant, PlantEntity.class);
    toSavePlant.setCreationDate(LocalDateTime.now());
    toSavePlant.setUpdateDate(LocalDateTime.now());
    toSavePlant.setIsActive(true);

    PlantEntity savedPlant = plantRepository.saveAndFlush(toSavePlant);
    return modelMapper.map(savedPlant, PlantDTO.class);
  }

  @Override
  public PlantDTO update(Long id, PutPlantDTO updatedPlant) {
    PlantEntity plantEntity = findEntityById(id);

    plantEntity.setReadings(updatedPlant.getReadings());
    plantEntity.setWarnings(updatedPlant.getWarnings());
    plantEntity.setAlerts(updatedPlant.getAlerts());
    plantEntity.setDisabledSensors(updatedPlant.getDisabledSensors());
    plantEntity.setUpdateDate(LocalDateTime.now());

    plantRepository.saveAndFlush(plantEntity);
    return modelMapper.map(plantEntity, PlantDTO.class);
  }

  @Override
  public void delete(Long id) {
    PlantEntity plantEntity = findEntityById(id);
    if (!plantEntity.getIsActive()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "The plant was already removed");
    }
    plantEntity.setIsActive(false);
    plantRepository.saveAndFlush(plantEntity);
  }

  private PlantEntity findEntityById(Long id) {
    return plantRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Plant not found"));
  }

}
