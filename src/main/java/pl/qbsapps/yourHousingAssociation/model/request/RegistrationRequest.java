package pl.qbsapps.yourHousingAssociation.model.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Size(min = 2, max = 255)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 255)
    @NotNull
    private String lastName;
}
