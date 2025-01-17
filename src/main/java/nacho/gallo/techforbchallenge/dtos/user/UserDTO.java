package nacho.gallo.techforbchallenge.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  @JsonProperty("id")
  private Long id;
  @JsonProperty("email")
  private String email;
  @JsonProperty("username")
  private String username;
}
