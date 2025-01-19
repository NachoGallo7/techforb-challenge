package nacho.gallo.techforbchallenge.controllers;

import nacho.gallo.techforbchallenge.clients.CountryClient;
import nacho.gallo.techforbchallenge.dtos.country.CountryDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("countries")
@RestController
public class CountryController {

  private final CountryClient countryClient;

  public CountryController(CountryClient countryClient) {
    this.countryClient = countryClient;
  }

  @GetMapping
  public ResponseEntity<List<CountryDTO>> getAll() {
    return ResponseEntity.ok(countryClient.getAll());
  }
}
