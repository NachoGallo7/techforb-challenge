package nacho.gallo.techforbchallenge.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLANTS")
public class Plant extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "country")
  private String country;
  @Column(name = "contry_code")
  private String contryCode;
  @Column(name = "readings")
  private Integer readings;
  @Column(name = "warnings")
  private Integer warnings;
  @Column(name = "alerts")
  private Integer alerts;
  @Column(name = "disabled_sensors")
  private Integer disabledSensors;
  @Column(name = "is_active")
  private Boolean isActive;
}
