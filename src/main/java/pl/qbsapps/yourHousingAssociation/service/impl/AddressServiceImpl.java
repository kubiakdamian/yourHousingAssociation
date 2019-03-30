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

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public void addUserAddress(String username, int blockNumber, String street, int apartmentNumber, String postalCode) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (checkIfAddressIsAlreadyAdded(user.getId())) {
            throw new AddressAlreadyAddedException();
        }

        Address address = new Address(blockNumber, street, apartmentNumber, postalCode, user);

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
