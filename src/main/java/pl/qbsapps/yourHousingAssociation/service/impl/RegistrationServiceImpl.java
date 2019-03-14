package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        final User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(registrationRequest.getPassword())
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .build();

        userRepository.save(user);
    }
}
