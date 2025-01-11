package nacho.gallo.techforbchallenge.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUserDTO {
  @NotEmpty(message = "Email cannot be empty")
  @Email(message = "The email needs to have an email format")
  @JsonProperty("email")
  private String email;
  @NotEmpty(message = "Password cannot be empty")
  @Size(min = 9, message = "The password must have more than 8 characters")
  @JsonProperty("password")
  private String password;
}
