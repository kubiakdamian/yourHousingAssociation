package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbsapps.yourHousingAssociation.model.Fee;

import java.util.Collection;
import java.util.Optional;

public interface FeeRepository extends JpaRepository<Fee, Long> {
    Collection<Fee> findAllByUserId(Long userId);

    Optional<Fee> findByUserId(Long userId);
}
