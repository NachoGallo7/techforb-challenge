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
public class PostPlantDTO {
  @JsonProperty("name")
  private String name;
  @JsonProperty("country")
  private String country;
}
