package pl.qbsapps.yourHousingAssociation.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PdfTranslationsHeaders {
    PL("Nazwa stawki", "Ilość", "Cena (pln)", "Suma (pln)"),
    EN("Bid name", "Quantity", "Price (pln)", "Sum (pln)"),
    DE("Rate name", "Anzahl", "Preis (pln)", "Summe (pln)");

    private String bidName;
    private String quantity;
    private String price;
    private String sum;
}
