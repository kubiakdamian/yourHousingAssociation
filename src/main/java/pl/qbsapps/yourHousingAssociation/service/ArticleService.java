package pl.qbsapps.yourHousingAssociation.service;

import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {
    void addArticle(String username, MultipartFile file, String plTitle, String plText, String enTitle, String enText, String deTitle, String deText);
}
