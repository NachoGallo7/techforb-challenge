package nacho.gallo.techforbchallenge.clients;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import nacho.gallo.techforbchallenge.dtos.country.CountryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Set;

@Service
public class CountryClient {

  private final String baseUrl;
  private final RestTemplate restTemplate;
  private final List<CountryDTO> gracefulResponseList = List.of(
    new CountryDTO("https://flagcdn.com/ar.svg", "Argentina"),
    new CountryDTO("https://flagcdn.com/bo.svg", "Bolivia"),
    new CountryDTO("https://flagcdn.com/br.svg", "Brazil"),
    new CountryDTO("https://flagcdn.com/cl.svg", "Chile"),
    new CountryDTO("https://flagcdn.com/co.svg", "Colombia"),
    new CountryDTO("https://flagcdn.com/cr.svg", "Costa Rica"),
    new CountryDTO("https://flagcdn.com/ec.svg", "Ecuador"),
    new CountryDTO("https://flagcdn.com/gt.svg", "Guatemala"),
    new CountryDTO("https://flagcdn.com/mx.svg", "Mexico"),
    new CountryDTO("https://flagcdn.com/py.svg", "Paraguay"),
    new CountryDTO("https://flagcdn.com/pe.svg", "Peru"),
    new CountryDTO("https://flagcdn.com/pr.svg", "Puerto Rico"),
    new CountryDTO("https://flagcdn.com/us.svg", "United States"),
    new CountryDTO("https://flagcdn.com/uy.svg", "Uruguay"),
    new CountryDTO("https://flagcdn.com/ve.svg", "Venezuela")
  );

  @Autowired
  public CountryClient(@Value("${client.config.countries-api-url}") String baseUrl, RestTemplate restTemplate) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
  }

  @CircuitBreaker(name = "countriesCB", fallbackMethod = "getAllFallback")
  public List<CountryDTO> getAll() {
    String url = UriComponentsBuilder.fromUriString(baseUrl)
        .queryParam("fields", "flags", "name", "cca2")
        .encode()
        .toUriString();

    ResponseEntity<List<CountryDTO>> response = restTemplate.exchange(
        url.replace("%20", " "),
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<CountryDTO>>() {}
    );

    if (response.hasBody()) {
      return response.getBody().stream().filter(country -> !country.getName().equalsIgnoreCase("Falkland Islands")).toList();
    } else {
      return List.of();
    }
  }

  private List<CountryDTO> getAllFallback(Exception ex) {
    System.out.println("COUNTRY FALLBACK");
    return gracefulResponseList;
  }
}
