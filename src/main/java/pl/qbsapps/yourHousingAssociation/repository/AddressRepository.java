package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbsapps.yourHousingAssociation.model.Address;

import java.util.Collection;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Collection<Address> findAllByUserId(Long userId);
}
