package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;

public interface RegistrationService {
    void registerUser(RegistrationRequest registrationRequest);

    void activateUser(String token);
}
