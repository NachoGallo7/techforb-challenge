package nacho.gallo.techforbchallenge.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLANTS")
public class PlantEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;
  @Column(name = "name")
  private String name;
  @Column(name = "country")
  private String country;
  @Column(name = "contry_code")
  private String countryCode;
  @Column(name = "readings")
  private Integer readings = 0;
  @Column(name = "warnings")
  private Integer warnings = 0;
  @Column(name = "alerts")
  private Integer alerts = 0;
  @Column(name = "disabled_sensors")
  private Integer disabledSensors = 0;
  @Column(name = "is_active")
  private Boolean isActive;
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity user;
  @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PlantDetailEntity> plantDetails = new LinkedHashSet<>();
}
