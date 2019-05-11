package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.request.PaymentRequest;
import pl.qbsapps.yourHousingAssociation.model.response.FeeStatusResponse;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

public interface FeeService {
    void addFees(String userName, double gas, double coldWater, double hotWater, double sewage);

    boolean isFeeFulfilled(String username);

    ArrayList<Fee> getNotAcceptedManagedFees(String username);

    void acceptManagedFee(String username, Long feeId);

    void declineManagedFee(String username, Long feeId);

    FeeStatusResponse getFeeStatus(String username);

    void payFee(String username, PaymentRequest paymentRequest);

    ByteArrayInputStream generatePDF(String username);
}
