package pl.qbsapps.yourHousingAssociation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("ADMIN"),
    TENANT("TENANT"),
    MANAGER("MANAGER");

    private String role;
}
