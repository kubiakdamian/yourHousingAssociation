package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.Data;

@Data
public class UserDataResponse {

    private String firstName;

    private String lastName;

    private String street;

    private String postalCode;

    private int blockNumber;

    private int apartmentNumber;
}
