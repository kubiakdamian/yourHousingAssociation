package pl.qbsapps.yourHousingAssociation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;
import pl.qbsapps.yourHousingAssociation.service.RegistrationService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        logger.debug("Registrating user with email - " + registrationRequest.getEmail());

        registrationService.registerUser(registrationRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/activate/{token}")
    public ResponseEntity activateUser(final @PathVariable("token") String token) {
        registrationService.activateUser(token);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/manager")
    public ResponseEntity createManager(@RequestBody @Valid RegistrationRequest registrationRequest, Principal user) {
        logger.debug("Creating new manager - " + registrationRequest.getEmail());

        registrationService.createManager(registrationRequest, user.getName());

        return new ResponseEntity(HttpStatus.CREATED);
    }
}
