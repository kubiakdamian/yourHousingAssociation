package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.Data;

@Data
public class FeeStatusResponse {
    private boolean isPaid;

    private boolean isVerified;
}
