package pl.qbsapps.yourHousingAssociation.service;

public interface FeeService {
    void addFees(String userName, float gas, float coldWater, float hotWater, float sewage, float heating, int repairFund);
}
