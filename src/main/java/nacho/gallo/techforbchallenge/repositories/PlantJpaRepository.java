package nacho.gallo.techforbchallenge.repositories;

import nacho.gallo.techforbchallenge.entities.PlantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlantJpaRepository extends JpaRepository<PlantEntity, Long> {
  Page<PlantEntity> findAllByUser_Id(Long userId, Pageable pageable);
  Optional<PlantEntity> findByIdAndUser_Id(Long id, Long userId);
}
