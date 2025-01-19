package nacho.gallo.techforbchallenge.dtos.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class PutPlantDTO {
  @JsonProperty("readings")
  @NotNull(message = "Readings cannot be empty")
  @Min(value = 0, message = "Readings has to be equal or greater than 0")
  private Integer readings;
  @JsonProperty("warnings")
  @NotNull(message = "Warnings cannot be empty")
  @Min(value = 0, message = "Warnings has to be equal or greater than 0")
  private Integer warnings;
  @JsonProperty("alerts")
  @NotNull(message = "Alerts cannot be empty")
  @Min(value = 0, message = "Alerts has to be equal or greater than 0")
  private Integer alerts;
  @JsonProperty("disabled_sensors")
  @NotNull(message = "Disabled Sensors cannot be empty")
  @Min(value = 0, message = "Disabled Sensors has to be equal or greater than 0")
  private Integer disabledSensors;
  @JsonProperty("plant_details")
  private Set<PlantDetail> plantDetails = new LinkedHashSet<>();
}
