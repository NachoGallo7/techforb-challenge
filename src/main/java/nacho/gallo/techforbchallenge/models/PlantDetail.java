package nacho.gallo.techforbchallenge.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlantDetail {
  @JsonProperty("id")
  private Long id;
  @JsonProperty("readings")
  private Integer readings = 0;
  @JsonProperty("warnings")
  private Integer warnings = 0;
  @JsonProperty("alerts")
  private Integer alerts = 0;
  @JsonProperty("disabled_sensors")
  private Integer disabledSensors = 0;
  @JsonProperty("plant_detail_type")
  private PlantDetailType plantDetailType;
}
