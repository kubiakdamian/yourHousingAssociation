package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.response.UserDataResponse;

import java.util.ArrayList;

public interface UserService {
    UserDataResponse getUserData(String username);

    void verifyUser(String verificationKey, String username);

    ArrayList<User> getAllManagers(String username);

    void deleteManager(String adminName, Long userId);
}
