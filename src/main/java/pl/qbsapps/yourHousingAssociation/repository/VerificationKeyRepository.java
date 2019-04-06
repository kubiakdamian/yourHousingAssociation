package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbsapps.yourHousingAssociation.model.VerificationKey;

public interface VerificationKeyRepository extends JpaRepository<VerificationKey, Long> {
}
