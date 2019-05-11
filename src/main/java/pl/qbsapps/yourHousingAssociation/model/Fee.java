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
import java.math.BigDecimal;

@Entity
@Data
public class Fee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String passingDate;

    @Column(nullable = false)
    private BigDecimal gas;

    @Column(nullable = false)
    private double gasUsage;

    @Column(nullable = false)
    private BigDecimal coldWater;

    @Column(nullable = false)
    private double coldWaterUsage;

    @Column(nullable = false)
    private BigDecimal hotWater;

    @Column(nullable = false)
    private double hotWaterUsage;

    @Column(nullable = false)
    private BigDecimal sewage;

    @Column(nullable = false)
    private double sewageUsage;

    @Column(nullable = false)
    private BigDecimal heating;

    @Column(nullable = false)
    private BigDecimal repairFund;

    @Column(nullable = false)
    private boolean isPaid;

    @Column(nullable = false)
    private boolean isVerified;

    @Column(nullable = false)
    private int paidMonth;

    @Column(nullable = false)
    private BigDecimal amountToPay;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;
}
