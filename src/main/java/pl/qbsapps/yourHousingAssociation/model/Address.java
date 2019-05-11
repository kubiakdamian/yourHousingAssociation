package pl.qbsapps.yourHousingAssociation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

@Entity
@NoArgsConstructor
@Data
public class Address {

    @Id
    @Column
    private Long id;

    @Column
    private String city;

    @Column
    private int blockNumber;

    @Column
    private String street;

    @Column
    private int streetNumber;

    @Column
    private int apartmentNumber;

    @Column
    @Size(min = 6, max = 6)
    private String postalCode;

    @Column
    private double apartmentSize;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;

    public Address(String city, int blockNumber, String street, int streetNumber, int apartmentNumber, String postalCode, double apartmentSize, User user) {
        this.city = city;
        this.blockNumber = blockNumber;
        this.street = street;
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.postalCode = postalCode;
        this.apartmentSize = apartmentSize;
        this.user = user;
    }
}
