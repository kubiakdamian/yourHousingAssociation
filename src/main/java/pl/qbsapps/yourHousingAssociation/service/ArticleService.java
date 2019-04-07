package pl.qbsapps.yourHousingAssociation.service;

import org.springframework.web.multipart.MultipartFile;
import pl.qbsapps.yourHousingAssociation.model.Article;

import java.util.Collection;

public interface ArticleService {
    void addArticle(String username, MultipartFile file, String plTitle, String plText, String enTitle, String enText, String deTitle, String deText);

    Collection<Article> getThreeNewestArticles();
}
