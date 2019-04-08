package pl.qbsapps.yourHousingAssociation.service;

import java.util.Date;

public interface FeeService {
    void addFees(String userName, float gas, float coldWater, float hotWater, float sewage, float heating, int repairFund);

    boolean isFeeFulfilled(String username);
}
