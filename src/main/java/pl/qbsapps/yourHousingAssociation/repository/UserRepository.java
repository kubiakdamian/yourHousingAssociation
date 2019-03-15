package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbsapps.yourHousingAssociation.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
