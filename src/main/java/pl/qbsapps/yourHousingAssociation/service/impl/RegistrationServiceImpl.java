package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.PermissionDeniedException;
import pl.qbsapps.yourHousingAssociation.exception.UserAlreadyExistsException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.AccountStatus;
import pl.qbsapps.yourHousingAssociation.model.ActivationToken;
import pl.qbsapps.yourHousingAssociation.model.Role;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;
import pl.qbsapps.yourHousingAssociation.repository.ActivationTokenRepository;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.ActivationTokenService;
import pl.qbsapps.yourHousingAssociation.service.MailSenderService;
import pl.qbsapps.yourHousingAssociation.service.RegistrationService;

import java.util.ArrayList;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ActivationTokenService activationTokenService;
    private final ActivationTokenRepository activationTokenRepository;
    private final MailSenderService mailSenderService;

    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ActivationTokenService activationTokenService, ActivationTokenRepository activationTokenRepository, MailSenderService mailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.activationTokenService = activationTokenService;
        this.activationTokenRepository = activationTokenRepository;
        this.mailSenderService = mailSenderService;
    }

    @Override
    @Transactional
    public void registerUser(RegistrationRequest registrationRequest, String lang) {
        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        final User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .status(AccountStatus.INACTIVE)
                .role(Role.TENANT)
                .isVerified(false)
                .build();

        userRepository.save(user);

        String token = generateTokenForUser(user);

        String mailContent = generateProperMailContent(lang, user, token);

        mailSenderService.sendEmail(registrationRequest.getEmail(), mailContent, lang);
    }

    @Override
    @Transactional
    public void activateUser(String token) {
        User user = activationTokenRepository.getUserIdByActivationToken(token)
                .orElseThrow(UserNotFoundException::new);

        user.setStatus(AccountStatus.ACTIVE);

        activationTokenRepository.deleteActivationTokenByToken(token);
    }

    @Override
    @Transactional
    public void createManager(RegistrationRequest registrationRequest, String username, int lowBlock, int highBlock) {
        User admin = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!admin.getRole().equals(Role.ADMIN)) {
            throw new PermissionDeniedException();
        }

        if (userRepository.findByEmail(registrationRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        ArrayList<Integer> blocks = new ArrayList<>();
        for (int i = lowBlock; i <= highBlock; i++) {
            blocks.add(i);
        }

        final User user = User.builder()
                .email(registrationRequest.getEmail())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .status(AccountStatus.ACTIVE)
                .role(Role.MANAGER)
                .isVerified(true)
                .blocksNumbers(blocks)
                .build();

        userRepository.save(user);
    }

    private String generateTokenForUser(User user) {
        final ActivationToken activationToken = new ActivationToken();
        String token = activationTokenService.generateActivationToken(user);

        activationToken.setToken(token);
        activationToken.setUser(user);

        activationTokenRepository.save(activationToken);

        return token;
    }

    private String generateProperMailContent(String lang, User user, String token) {
        String mailContent;
        switch (lang) {
            case "pl":
                mailContent = mailSenderService.createEmailContent(
                        "Witaj " + user.getEmail().substring(0, user.getEmail().indexOf("@")) + "!",
                        "Dziekujemy za rejestracje w serwisie yourHousingAssociation. Ponizej znajduje sie link aktywacyjny, kliknij go, aby dokonczyc rejestracje",
                        "Kliknij w ten link, aby potwierdzic adres e-mail",
                        token);
                break;

            case "en":
                mailContent = mailSenderService.createEmailContent(
                        "Hello " + user.getEmail().substring(0, user.getEmail().indexOf("@")) + "!",
                        "Thank you for signing up in yourHousingAssociation. Below is an activation link, click it to complete your registration",
                        "Click this link to confirm your email address",
                        token);
                break;

            case "de":
                mailContent = mailSenderService.createEmailContent(
                        "Willkommen " + user.getEmail().substring(0, user.getEmail().indexOf("@")) + "!",
                        "Vielen Dank, dass Sie sich bei Ihrer Wohnungsbaugesellschaft angemeldet haben. Unten finden Sie einen Aktivierungslink. Klicken Sie darauf, um Ihre Registrierung abzuschließen",
                        "Klicken Sie auf diesen Link, um Ihre E-Mail-Adresse zu bestätigen",
                        token);
                break;

            default:
                mailContent = mailSenderService.createEmailContent(
                        "Hello " + user.getEmail().substring(0, user.getEmail().indexOf("@")) + "!",
                        "Thank you for signing up in yourHousingAssociation. Below is an activation link, click it to complete your registration",
                        "Click this link to confirm your email address",
                        token);
                break;
        }

        return mailContent;
    }
}
