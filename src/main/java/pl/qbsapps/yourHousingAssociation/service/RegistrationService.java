package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest, String lang);

    void activateUser(String token);

    void createManager(RegistrationRequest registrationRequest, String username, int lowBlock, int highBlock);
}
