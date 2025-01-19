package nacho.gallo.techforbchallenge.controllers;

import jakarta.validation.Valid;
import nacho.gallo.techforbchallenge.clients.CountryClient;
import nacho.gallo.techforbchallenge.dtos.country.CountryDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PostPlantDTO;
import nacho.gallo.techforbchallenge.dtos.plant.PutPlantDTO;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
                                               @RequestParam(defaultValue = "DESC", name = "sort_direction") Sort.Direction sortDirection,
                                               @RequestHeader(name = "Authorization") String authToken) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort.split(",")));
    return ResponseEntity.ok(plantService.getAll(pageable, authToken));
  }

  @GetMapping("{id}")
  public ResponseEntity<PlantDTO> getById(@PathVariable Long id,
                                          @RequestHeader(name = "Authorization") String authToken) {
    return ResponseEntity.ok(plantService.getById(id, authToken));
  }

  @PostMapping
  public ResponseEntity<PlantDTO> create(@RequestBody @Valid PostPlantDTO newPlant,
                                         @RequestHeader(name = "Authorization") String authToken) {
    PlantDTO test = plantService.create(newPlant, authToken);
    return ResponseEntity.ok(test);
  }

  @PutMapping("{id}")
  public ResponseEntity<PlantDTO> update(@PathVariable Long id, @RequestBody @Valid PutPlantDTO updatedPlant,
                                         @RequestHeader(name = "Authorization") String authToken) {
    return ResponseEntity.ok(plantService.update(id, updatedPlant, authToken));
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id,
                                     @RequestHeader(name = "Authorization") String authToken) {
    plantService.delete(id, authToken);
    return ResponseEntity.noContent().build();
  }

}
