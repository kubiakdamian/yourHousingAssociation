package pl.qbsapps.yourHousingAssociation.service;

import pl.qbsapps.yourHousingAssociation.model.Fee;
import pl.qbsapps.yourHousingAssociation.model.response.FeeStatusResponse;

import java.util.ArrayList;

public interface FeeService {
    void addFees(String userName, float gas, float coldWater, float hotWater, float sewage, float heating, int repairFund);

    boolean isFeeFulfilled(String username);

    ArrayList<Fee> getNotAcceptedManagedFees(String username);

    void acceptManagedFee(String username, Long feeId);

    void declineManagedFee(String username, Long feeId);

    FeeStatusResponse getFeeStatus(String username);
}
