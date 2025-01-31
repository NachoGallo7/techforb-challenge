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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CountryClient {

  private final String baseUrl;
  private final RestTemplate restTemplate;
  private final List<CountryDTO> gracefulResponseList = List.of(
      new CountryDTO("https://flagcdn.com/ar.svg", "Argentina", "AR"),
      new CountryDTO("https://flagcdn.com/bo.svg", "Bolivia", "BO"),
      new CountryDTO("https://flagcdn.com/br.svg", "Brazil", "BR"),
      new CountryDTO("https://flagcdn.com/cl.svg", "Chile", "CL"),
      new CountryDTO("https://flagcdn.com/co.svg", "Colombia", "CO"),
      new CountryDTO("https://flagcdn.com/cr.svg", "Costa Rica", "CR"),
      new CountryDTO("https://flagcdn.com/ec.svg", "Ecuador", "EC"),
      new CountryDTO("https://flagcdn.com/gt.svg", "Guatemala", "GT"),
      new CountryDTO("https://flagcdn.com/mx.svg", "Mexico", "MX"),
      new CountryDTO("https://flagcdn.com/py.svg", "Paraguay", "PY"),
      new CountryDTO("https://flagcdn.com/pe.svg", "Peru", "PE"),
      new CountryDTO("https://flagcdn.com/pr.svg", "Puerto Rico", "PR"),
      new CountryDTO("https://flagcdn.com/us.svg", "United States", "US"),
      new CountryDTO("https://flagcdn.com/uy.svg", "Uruguay", "UY"),
      new CountryDTO("https://flagcdn.com/ve.svg", "Venezuela", "VE")
  );
  private List<CountryDTO> countryCacheList = new ArrayList<>();

  @Autowired
  public CountryClient(@Value("${client.config.countries-api-url}") String baseUrl, RestTemplate restTemplate) {
    this.baseUrl = baseUrl;
    this.restTemplate = restTemplate;
  }

  @CircuitBreaker(name = "countriesCB", fallbackMethod = "getAllFallback")
  public List<CountryDTO> getAll() {
    if(countryCacheList.size() > 0) {
      return countryCacheList;
    };

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
      List<CountryDTO> result = response.getBody().stream().filter(country -> !country.getName().equalsIgnoreCase("Falkland Islands")).toList();
      countryCacheList = result;
      return result;
    } else {
      return List.of();
    }
  }

  private List<CountryDTO> getAllFallback(Exception ex) {
    return gracefulResponseList;
  }
}
