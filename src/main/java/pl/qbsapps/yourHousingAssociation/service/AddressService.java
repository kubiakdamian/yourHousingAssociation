package pl.qbsapps.yourHousingAssociation.service;

public interface AddressService {
    void addUserAddress(String username, String city, int blockNumber, String street, int streetNumber, int apartmentNumber, String postalCode);


}
