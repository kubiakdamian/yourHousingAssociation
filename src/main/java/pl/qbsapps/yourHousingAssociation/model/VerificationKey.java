package pl.qbsapps.yourHousingAssociation.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Data
@Entity
public class VerificationKey {

    @Id
    @Column
    private Long id;

    @Column
    private String key;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
