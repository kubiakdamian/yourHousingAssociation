package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.AddressAlreadyAddedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.Address;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.repository.AddressRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.AddressService;
import pl.qbsapps.yourHousingAssociation.service.VerificationKeyService;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final VerificationKeyService verificationKeyService;

    @Autowired
    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository, VerificationKeyService verificationKeyService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.verificationKeyService = verificationKeyService;
    }

    @Override
    @Transactional
    public void addUserAddress(String username, String city, int blockNumber, String street, int streetNumber, int apartmentNumber, String postalCode, double apartmentSize) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (checkIfAddressIsAlreadyAdded(user.getId())) {
            throw new AddressAlreadyAddedException();
        }

        Address address = new Address(city, blockNumber, street, streetNumber, apartmentNumber, postalCode, apartmentSize, user);

        verificationKeyService.generateKey(user);
        addressRepository.save(address);
    }

    private boolean checkIfAddressIsAlreadyAdded(Long userId) {
        for (Address address : addressRepository.findAllByUserId(userId)) {
            if (address.getUser().getId().equals(userId)) {
                return true;
            }
        }

        return false;
    }
}
