package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.User;

public interface VerificationKeyService {
    void generateKey(User user);
}
