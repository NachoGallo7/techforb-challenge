package nacho.gallo.techforbchallenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLANTS")
public class PlantEntity extends BaseEntity{
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
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
}
