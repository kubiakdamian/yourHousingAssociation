package pl.qbsapps.yourHousingAssociation.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public enum Prices {
    HOT_WATER(BigDecimal.valueOf(30)),
    COLD_WATER(BigDecimal.valueOf(9)),
    HEATING(BigDecimal.valueOf(2.75)),
    GAS(BigDecimal.valueOf(1.3)),
    REPAIR_FUND(BigDecimal.valueOf(1.5)),
    SEWAGE(BigDecimal.valueOf(0.3));


    private BigDecimal price;
}
