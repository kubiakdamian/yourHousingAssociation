package pl.qbsapps.yourHousingAssociation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.qbsapps.yourHousingAssociation.model.Article;
import pl.qbsapps.yourHousingAssociation.service.ArticleService;

import java.security.Principal;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity addArticle(Principal user, @RequestParam("file") MultipartFile file,
                                     @RequestParam("plTitle") String plTitle, @RequestParam("plText") String plText,
                                     @RequestParam("enTitle") String enTitle, @RequestParam("enText") String enText,
                                     @RequestParam("deTitle") String deTitle, @RequestParam("deText") String deText) {
        articleService.addArticle(user.getName(), file, plTitle, plText, enTitle, enText, deTitle, deText);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/newest")
    public ResponseEntity<?> getThreeNewestArticles() {
        ArrayList<Article> articles = (ArrayList<Article>) articleService.getThreeNewestArticles();

        return ResponseEntity.ok(articles);
    }
}
