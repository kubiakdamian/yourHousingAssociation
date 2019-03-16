package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.exception.UserNotFoundException;
import pl.qbsapps.yourHousingAssociation.model.User;
import pl.qbsapps.yourHousingAssociation.model.request.AuthenticationRequest;
import pl.qbsapps.yourHousingAssociation.model.response.AuthenticationResponse;
import pl.qbsapps.yourHousingAssociation.repository.UserRepository;
import pl.qbsapps.yourHousingAssociation.service.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final AuthService authService;
    private final UserRepository userRepository;

    @Autowired
    public UserController(AuthService authService, UserRepository userRepository) {
        this.authService = authService;
        this.userRepository = userRepository;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> signIn(@Valid @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authService.auth(authenticationRequest);
        String token = authService.getAuthenticationToken(authentication);

        User user = userRepository.findByEmail(authenticationRequest.getLogin())
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }
}
