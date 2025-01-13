package nacho.gallo.techforbchallenge.services;

import nacho.gallo.techforbchallenge.dtos.plant.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PutPlantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PlantService {
  Page<PlantDTO> getAll(Pageable pageable, String authToken);
  PlantDTO getById(Long id, String authToken);
  PlantDTO create(PostPlantDTO newPlant, String authToken);
  PlantDTO update(Long id, PutPlantDTO updatedPlant, String authToken);
  void delete(Long id, String authToken);
}
