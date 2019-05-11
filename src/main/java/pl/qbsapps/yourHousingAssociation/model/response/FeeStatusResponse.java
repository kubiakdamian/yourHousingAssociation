package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FeeStatusResponse {
    private boolean isPaid;

    private boolean isVerified;

    private BigDecimal amount;
}
