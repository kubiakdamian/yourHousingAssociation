package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.response.UserDataResponse;

public interface UserService {
    UserDataResponse getUserData(String username);

    void verifyUser(String verificationKey, String username);
}
