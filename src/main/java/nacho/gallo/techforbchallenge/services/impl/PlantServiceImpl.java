package nacho.gallo.techforbchallenge.services.impl;

import nacho.gallo.techforbchallenge.dtos.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.PutPlantDTO;
import nacho.gallo.techforbchallenge.services.PlantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlantServiceImpl implements PlantService {
  @Override
  public Page<PlantDTO> getAll(Pageable pageable) {
    return null;
  }

  @Override
  public PlantDTO getById(Long id) {
    return null;
  }

  @Override
  public PlantDTO create(PostPlantDTO newPlant) {
    return null;
  }

  @Override
  public PlantDTO update(PutPlantDTO updatedPlant) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
