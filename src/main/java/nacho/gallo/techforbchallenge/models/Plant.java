package nacho.gallo.techforbchallenge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
  @JsonProperty("id")
  private Long id;
  @JsonProperty("name")
  private String name;
  @JsonProperty("country")
  private String country;
  @JsonProperty("country_code")
  private String countryCode;
  @JsonProperty("readings")
  private Integer readings;
  @JsonProperty("warnings")
  private Integer warnings;
  @JsonProperty("alerts")
  private Integer alerts;
  @JsonProperty("disabled_sensors")
  private Integer disabledSensors;
  @JsonProperty("is_active")
  private Boolean isActive;
  @JsonProperty("user")
  private User user;
  @JsonProperty("plant_details")
  private Set<PlantDetail> plantDetails = new LinkedHashSet<>();
}
