package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.AuthenticationRequest;
import pl.qbsapps.yourHousingAssociation.model.response.AuthenticationResponse;
import pl.qbsapps.yourHousingAssociation.model.response.TenantResponse;
import pl.qbsapps.yourHousingAssociation.model.response.UserDataResponse;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.AddressService;
import pl.qbsapps.yourHousingAssociation.service.AuthService;
import pl.qbsapps.yourHousingAssociation.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final AddressService addressService;
    private final UserService userService;

    @Autowired
    public UserController(AuthService authService, UserRepository userRepository, AddressService addressService, UserService userService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authService.auth(authenticationRequest);
        String token = authService.getAuthenticationToken(authentication);

        User user = userRepository.findByEmail(authenticationRequest.getLogin())
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(new AuthenticationResponse(token, user.getRole(), user.isVerified()));
    }

    @PostMapping(value = "/address/{city}/{blockNumber}/{street}/{streetNumber}/{apartmentNumber}/{postalCode}/{apartmentSize}")
    public ResponseEntity addAddress(Principal user, @PathVariable String city, @PathVariable int blockNumber,
                                     @PathVariable String street, @PathVariable int streetNumber,
                                     @PathVariable int apartmentNumber, @PathVariable String postalCode,
                                     @PathVariable double apartmentSize) {
        addressService.addUserAddress(user.getName(), city, blockNumber, street, streetNumber, apartmentNumber, postalCode, apartmentSize);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/personalData")
    public ResponseEntity<?> getPersonalData(Principal user) {
        UserDataResponse userDataResponse = userService.getUserData(user.getName());

        return ResponseEntity.ok(userDataResponse);
    }

    @PostMapping(value = "/verify/{verificationKey}")
    public ResponseEntity verify(Principal user, @PathVariable String verificationKey) {
        userService.verifyUser(verificationKey, user.getName());

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/managers")
    public ResponseEntity<?> getManagers(Principal user) {
        ArrayList<User> managers = userService.getAllManagers(user.getName());

        return ResponseEntity.ok(managers);
    }

    @DeleteMapping(value = "/manager/{managerId}")
    public ResponseEntity deleteManager(Principal user, @PathVariable Long managerId) {
        userService.deleteManager(user.getName(), managerId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/tenant/{tenantEmail:.+}")
    public ResponseEntity<?> getTenant(Principal user, @PathVariable String tenantEmail) {
        TenantResponse tenant = userService.getTenantByUsername(user.getName(), tenantEmail);

        return ResponseEntity.ok(tenant);
    }
}
