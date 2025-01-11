package nacho.gallo.techforbchallenge.dtos.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
  @JsonProperty("contry_code")
  private String contryCode;
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
}
