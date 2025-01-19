package nacho.gallo.techforbchallenge.dtos.plant;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostPlantDTO {
  @JsonProperty("name")
  @NotEmpty(message = "Name cannot be empty")
  private String name;
  @JsonProperty("country")
  @NotEmpty(message = "Country cannot be empty")
  private String country;
  @JsonProperty("country_code")
  @NotEmpty(message = "Contry code cannot be empty")
  private String countryCode;
}
