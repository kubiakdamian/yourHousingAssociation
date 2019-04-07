package pl.qbsapps.yourHousingAssociation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.qbsapps.yourHousingAssociation.model.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
