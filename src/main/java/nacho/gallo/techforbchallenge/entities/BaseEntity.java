package nacho.gallo.techforbchallenge.entities;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
  private LocalDateTime creationDate;
  private LocalDateTime updateDate;
}
