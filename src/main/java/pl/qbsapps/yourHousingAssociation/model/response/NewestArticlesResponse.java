package pl.qbsapps.yourHousingAssociation.model.response;

import lombok.Data;

@Data
public class NewestArticlesResponse {
    private String title;

    private String text;

    private String imageUrl;
}
