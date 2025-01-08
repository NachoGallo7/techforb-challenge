package nacho.gallo.techforbchallenge.controllers;

import nacho.gallo.techforbchallenge.dtos.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.PutPlantDTO;
import nacho.gallo.techforbchallenge.services.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("plants")
public class PlantController {

  private final PlantService plantService;

  @Autowired
  public PlantController(PlantService plantService) {
    this.plantService = plantService;
  }

  @GetMapping
  public ResponseEntity<Page<PlantDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size,
                                               @RequestParam(defaultValue = "updateDate") String sort,
                                               @RequestParam(defaultValue = "DESC", name = "sort_direction") Sort.Direction sortDirection) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort.split(",")));
    return ResponseEntity.ok(plantService.getAll(pageable));
  }

  @GetMapping("{id}")
  public ResponseEntity<PlantDTO> getById(@PathVariable Long id) {
    return ResponseEntity.ok(plantService.getById(id));
  }

  @PostMapping
  public ResponseEntity<PlantDTO> create(@RequestBody PostPlantDTO newPlant) {
    return ResponseEntity.ok(plantService.create(newPlant));
  }

  @PutMapping("{id}")
  public ResponseEntity<PlantDTO> update(@PathVariable Long id, @RequestBody PutPlantDTO updatedPlant) {
    return ResponseEntity.ok(plantService.update(id, updatedPlant));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    plantService.delete(id);
    return ResponseEntity.noContent().build();
  }

}
