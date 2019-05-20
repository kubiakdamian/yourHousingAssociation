package pl.qbsapps.yourHousingAssociation.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PdfTranslations {
    PL("Ciepła woda", "Zimna woda", "Gaz", "Ścieki", "Ogrzewanie", "Fundusz remontowy"),
    EN("Hot water", "Cold water", "Gas", "Sewage", "Heating", "Repair fund"),
    DE("Heißes Wasser", "Kaltes Wasser", "Gas", "Abwasser", "Heizung", "Reparaturfond");

    private String hotWater;
    private String coldWater;
    private String gas;
    private String sewage;
    private String heating;
    private String repairFund;
}
