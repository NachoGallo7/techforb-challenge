package nacho.gallo.techforbchallenge.services;

import nacho.gallo.techforbchallenge.dtos.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.PutPlantDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PlantService {
  Page<PlantDTO> getAll(Pageable pageable);
  PlantDTO getById(Long id);
  PlantDTO create(PostPlantDTO newPlant);
  PlantDTO update(PutPlantDTO updatedPlant);
  void delete(Long id);
}
