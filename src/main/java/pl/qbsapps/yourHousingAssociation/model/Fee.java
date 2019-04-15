package pl.qbsapps.yourHousingAssociation.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Data
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String passingDate;

    @Column(nullable = false)
    private float gas;

    @Column(nullable = false)
    private float coldWater;

    @Column(nullable = false)
    private float hotWater;

    @Column(nullable = false)
    private float sewage;

    @Column(nullable = false)
    private float heating;

    @Column(nullable = false)
    private int repairFund;

    @Column(nullable = false)
    private boolean isPaid;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private int paidMonth;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
