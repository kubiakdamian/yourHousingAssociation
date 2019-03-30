package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.response.UserDataResponse;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDataResponse getUserData(String username) {
        UserDataResponse userDataResponse = new UserDataResponse();

        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        userDataResponse.setFirstName(user.getFirstName());
        userDataResponse.setLastName(user.getLastName());
        userDataResponse.setBlockNumber(user.getAddress().getBlockNumber());
        userDataResponse.setStreet(user.getAddress().getStreet());
        userDataResponse.setApartmentNumber(user.getAddress().getApartmentNumber());
        userDataResponse.setPostalCode(user.getAddress().getPostalCode());

        return userDataResponse;
    }
}
