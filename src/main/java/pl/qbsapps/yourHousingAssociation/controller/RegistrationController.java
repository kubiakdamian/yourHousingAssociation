package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.qbsapps.yourHousingAssociation.model.request.RegistrationRequest;
import pl.qbsapps.yourHousingAssociation.service.RegistrationService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public ResponseEntity registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        registrationService.registerUser(registrationRequest);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping
    public String test() {
        return "test";
    }
}
