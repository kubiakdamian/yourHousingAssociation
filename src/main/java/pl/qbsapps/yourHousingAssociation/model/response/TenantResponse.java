package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponse {
    private String email;

    private String firstName;

    private String lastName;

    private String key;

    private String city;

    private String street;

    private int streetNumber;

    private int apartmentNumber;

    private int blockNumber;

    private double apartmentSize;
}
