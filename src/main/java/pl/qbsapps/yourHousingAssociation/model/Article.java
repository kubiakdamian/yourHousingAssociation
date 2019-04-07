package pl.qbsapps.yourHousingAssociation.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String plTitle;

    @Column(nullable = false)
    private String enTitle;

    @Column(nullable = false)
    private String deTitle;

    @Column(nullable = false)
    private String plText;

    @Column(nullable = false)
    private String enText;

    @Column(nullable = false)
    private String deText;

    @Column
    private String imageUrl;
}
