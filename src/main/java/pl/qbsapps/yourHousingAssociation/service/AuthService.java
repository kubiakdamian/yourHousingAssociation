package pl.qbsapps.yourHousingAssociation.service;

import org.springframework.security.core.Authentication;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.AuthenticationRequest;

public interface AuthService {
    Authentication auth(AuthenticationRequest authenticationRequest);

    String getAuthenticationToken(Authentication authentication);

    User getCurrentUser();
}
