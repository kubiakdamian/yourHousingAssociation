package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.qbsapps.yourHousingAssociation.exception.ManagersNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.PermissionDeniedException;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.VerificationKeyNotFoundException;
import pl.qbsapps.yourHousingAssociation.exception.WrongVerificationKeyException;
import pl.qbsapps.yourHousingAssociation.model.Role;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.VerificationKey;
import pl.qbsapps.yourHousingAssociation.model.response.TenantResponse;
import pl.qbsapps.yourHousingAssociation.model.response.UserDataResponse;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.repository.VerificationKeyRepository;
import pl.qbsapps.yourHousingAssociation.service.FeeService;
import pl.qbsapps.yourHousingAssociation.service.UserService;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VerificationKeyRepository verificationKeyRepository;
    private final FeeService feeService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, VerificationKeyRepository verificationKeyRepository, FeeService feeService) {
        this.userRepository = userRepository;
        this.verificationKeyRepository = verificationKeyRepository;
        this.feeService = feeService;
    }

    @Override
    public UserDataResponse getUserData(String username) {
        UserDataResponse userDataResponse = new UserDataResponse();

        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        userDataResponse.setFirstName(user.getFirstName());
        userDataResponse.setLastName(user.getLastName());
        userDataResponse.setCity(user.getAddress().getCity());
        userDataResponse.setBlockNumber(user.getAddress().getBlockNumber());
        userDataResponse.setStreet(user.getAddress().getStreet());
        userDataResponse.setStreetNumber(user.getAddress().getStreetNumber());
        userDataResponse.setApartmentNumber(user.getAddress().getApartmentNumber());
        userDataResponse.setPostalCode(user.getAddress().getPostalCode());
        userDataResponse.setVerified(user.isVerified());

        return userDataResponse;
    }

    @Override
    @Transactional
    public void verifyUser(String verificationKey, String username) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        VerificationKey key = verificationKeyRepository.findById(user.getId()).orElseThrow(VerificationKeyNotFoundException::new);

        if (!verificationKey.equals(key.getKey())) {
            throw new WrongVerificationKeyException();
        }

        user.setVerified(true);
    }

    @Override
    public ArrayList<User> getAllManagers(String username) {
        checkIfUserHasRequiredPermissions(username, Role.ADMIN);

        ArrayList<User> managers = (ArrayList<User>) userRepository.findAllByRole(Role.MANAGER);

        if (managers.isEmpty()) {
            throw new ManagersNotFoundException();
        }

        return managers;
    }

    @Override
    @Transactional
    public void deleteManager(String adminName, Long userId) {
        checkIfUserHasRequiredPermissions(adminName, Role.ADMIN);

        User manager = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        checkIfUserHasRequiredPermissions(manager.getUsername(), Role.MANAGER);

        userRepository.delete(manager);
    }

    @Override
    public TenantResponse getTenantByUsername(String managerName, String tenantEmail) {
        checkIfUserHasRequiredPermissions(managerName, Role.MANAGER);

        User tenant = userRepository.findByEmail(tenantEmail).orElseThrow(UserNotFoundException::new);
        if (!tenant.getRole().equals(Role.TENANT)) {
            throw new UserNotFoundException();
        }

        return new TenantResponse(tenant.getEmail(), tenant.getFirstName(), tenant.getLastName(), tenant.getVerificationKey().getKey());
    }

    @Override
    public ArrayList<User> getAllUsersWithUnfilledFee() {
        ArrayList<User> users = (ArrayList<User>) userRepository.findAllByRole(Role.TENANT);
        ArrayList<User> usersWithUnfilledFee = new ArrayList<>();

        for (User user : users) {
            System.out.println(feeService.isFeeFulfilled(user.getEmail()));
            usersWithUnfilledFee.add(user);
        }

        return usersWithUnfilledFee;
    }

    public void checkIfUserHasRequiredPermissions(String username, Role role) {
        User user = userRepository.findByEmail(username).orElseThrow(UserNotFoundException::new);

        if (!user.getRole().equals(role)) {
            throw new PermissionDeniedException();
        }
    }
}
