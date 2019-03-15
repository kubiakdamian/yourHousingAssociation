package pl.qbsapps.yourHousingAssociation.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private String status;
}
