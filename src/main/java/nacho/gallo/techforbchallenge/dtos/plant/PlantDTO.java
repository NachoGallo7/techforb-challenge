package nacho.gallo.techforbchallenge.dtos.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nacho.gallo.techforbchallenge.models.PlantDetail;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantDTO {
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
  @JsonProperty("plant_details")
  private Set<PlantDetail> plantDetails = new LinkedHashSet<>();
}
