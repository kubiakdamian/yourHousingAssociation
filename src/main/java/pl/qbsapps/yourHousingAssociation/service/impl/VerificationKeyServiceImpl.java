package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.VerificationKey;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.repository.VerificationKeyRepository;
import pl.qbsapps.yourHousingAssociation.service.VerificationKeyService;

import java.util.Random;

@Service
public class VerificationKeyServiceImpl implements VerificationKeyService {

    private final UserRepository userRepository;
    private final VerificationKeyRepository verificationKeyRepository;

    @Autowired
    public VerificationKeyServiceImpl(UserRepository userRepository, VerificationKeyRepository verificationKeyRepository) {
        this.userRepository = userRepository;
        this.verificationKeyRepository = verificationKeyRepository;
    }

    @Override
    @Transactional
    public void generateKey(User user) {
        VerificationKey verificationKey = new VerificationKey();

        Random rand = new Random();
        int code = rand.nextInt(9000000) + 1000000;

        verificationKey.setKey(String.valueOf(code));
        verificationKey.setUser(user);

        verificationKeyRepository.save(verificationKey);
    }
}
