package nacho.gallo.techforbchallenge.repositories;

import nacho.gallo.techforbchallenge.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantJpaRepository extends JpaRepository<PlantEntity, Long> {
}
