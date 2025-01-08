package nacho.gallo.techforbchallenge.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorApiDTO {
  private String timestamp;
  private Integer status;
  private String error;
  private String message;

}
