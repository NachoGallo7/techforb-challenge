package nacho.gallo.techforbchallenge.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutPlantDTO {
  @JsonProperty("readings")
  private Integer readings;
  @JsonProperty("warnings")
  private Integer warnings;
  @JsonProperty("alerts")
  private Integer alerts;
  @JsonProperty("disabled_sensors")
  private Integer disabledSensors;
}
