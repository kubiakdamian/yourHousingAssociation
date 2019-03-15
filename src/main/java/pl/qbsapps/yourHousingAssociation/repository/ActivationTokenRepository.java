package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.qbsapps.yourHousingAssociation.model.ActivationToken;
import pl.qbsapps.yourHousingAssociation.model.User;

import java.util.Optional;

public interface ActivationTokenRepository extends JpaRepository<ActivationToken, Long> {
    @Query("SELECT at.user FROM ActivationToken at WHERE at.token = :token")
    Optional<User> getUserIdByActivationToken(@Param("token") String token);

    void deleteActivationTokenByToken(String token);

    Optional<ActivationToken> findByUserEmail(String email);
}
