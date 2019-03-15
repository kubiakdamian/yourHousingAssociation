package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.User;

public interface ActivationTokenService {
    String generateActivationToken(User user);
}
