package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.AuthenticationRequest;
import pl.qbsapps.yourHousingAssociation.model.response.AuthenticationResponse;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.AddressService;
import pl.qbsapps.yourHousingAssociation.service.AuthService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final AddressService addressService;

    @Autowired
    public UserController(AuthService authService, UserRepository userRepository, AddressService addressService) {
        this.authService = authService;
        this.userRepository = userRepository;
        this.addressService = addressService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authService.auth(authenticationRequest);
        String token = authService.getAuthenticationToken(authentication);

        User user = userRepository.findByEmail(authenticationRequest.getLogin())
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(new AuthenticationResponse(token, user.getRole(), user.isVerified()));
    }

    @PostMapping(value = "/address/{blockNumber}/{street}/{apartmentNumber}/{postalCode}")
    public ResponseEntity addAddress(Principal user, @PathVariable int blockNumber, @PathVariable String street,
                                     @PathVariable int apartmentNumber, @PathVariable String postalCode) {
        addressService.addUserAddress(user.getName(), blockNumber, street, apartmentNumber, postalCode);

        return new ResponseEntity(HttpStatus.OK);
    }
}
