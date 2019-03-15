package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.stereotype.Service;
import pl.qbsapps.yourHousingAssociation.exception.UserAlreadyExistsException;
import pl.qbsapps.yourHousingAssociation.model.AccountStatus;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.service.ActivationTokenService;

import java.util.UUID;

@Service
public class ActivationTokenServiceImpl implements ActivationTokenService {
    @Override
    public String generateActivationToken(User user) {
        if(AccountStatus.ACTIVE.equals(user.getStatus())) {
            throw new UserAlreadyExistsException();
        }

        return UUID.randomUUID().toString();
    }
}
