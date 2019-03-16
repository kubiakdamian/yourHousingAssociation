package pl.qbsapps.yourHousingAssociation.model.request;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class AuthenticationRequest {
    @Email
    private String login;

    private String password;
}
