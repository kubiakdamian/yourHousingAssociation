package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.UserAlreadyExistsException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.AccountStatus;
import pl.qbsapps.yourHousingAssociation.model.ActivationToken;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;
import pl.qbsapps.yourHousingAssociation.repository.ActivationTokenRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.ActivationTokenService;
import pl.qbsapps.yourHousingAssociation.service.RegistrationService;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenService activationTokenService;
    private final ActivationTokenRepository activationTokenRepository;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ActivationTokenService activationTokenService, ActivationTokenRepository activationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationTokenService = activationTokenService;
        this.activationTokenRepository = activationTokenRepository;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest) {
        if(userRepository.findByEmail(registrationRequest.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        }

        final User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .status(AccountStatus.INACTIVE)
                .build();

        userRepository.save(user);


    }

    @Override
    public void activateUser(String token) {
        User user = activationTokenRepository.getUserIdByActivationToken(token)
                .orElseThrow(UserNotFoundException::new);

        user.setStatus(AccountStatus.ACTIVE);

        activationTokenRepository.deleteActivationTokenByToken(token);
    }

    private String generateTokenForUser(User user) {
        final ActivationToken activationToken = new ActivationToken();
        String token = activationTokenService.generateActivationToken(user);

        activationToken.setToken(token);
        activationToken.setUser(user);

        activationTokenRepository.save(activationToken);

        return token;
    }
}
