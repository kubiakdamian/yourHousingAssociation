package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.Data;
import pl.qbsapps.yourHousingAssociation.model.Role;

@Data
public class AuthenticationResponse {
    private String token;
    private Role role;

    public AuthenticationResponse(String token, Role role) {
        this.token = token;
        this.role = role;
    }
}
