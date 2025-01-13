package nacho.gallo.techforbchallenge.repositories;

import nacho.gallo.techforbchallenge.entities.RevokedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RevokedTokensJpaRepository extends JpaRepository<RevokedTokenEntity, String> {
}
