package nacho.gallo.techforbchallenge.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import nacho.gallo.techforbchallenge.models.PlantDetailType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PLANT_DETAILS")
@Entity
public class PlantDetailEntity {

  public PlantDetailEntity(PlantEntity plant, PlantDetailType plantDetailType) {
    this.plant = plant;
    this.plantDetailType = plantDetailType;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "readings")
  private Integer readings = 0;
  @Column(name = "warnings")
  private Integer warnings = 0;
  @Column(name = "alerts")
  private Integer alerts = 0;
  @Column(name = "disabled_sensors")
  private Integer disabledSensors = 0;
  @Enumerated(EnumType.STRING)
  @Column(name = "plant_detail_type")
  private PlantDetailType plantDetailType;
  @ManyToOne
  @JoinColumn(name = "plant_id")
  private PlantEntity plant;
}
