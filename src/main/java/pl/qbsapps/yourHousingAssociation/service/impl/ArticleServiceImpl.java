package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.qbsapps.yourHousingAssociation.model.Article;
import pl.qbsapps.yourHousingAssociation.model.Role;
import pl.qbsapps.yourHousingAssociation.model.response.NewestArticlesResponse;
import pl.qbsapps.yourHousingAssociation.repository.ArticleRepository;
import pl.qbsapps.yourHousingAssociation.service.ArticleService;
import pl.qbsapps.yourHousingAssociation.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final HttpServletRequest request;

    @Autowired
    public ArticleServiceImpl(UserService userService, ArticleRepository articleRepository, HttpServletRequest request) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.request = request;
    }

    @Override
    @Transactional
    public void addArticle(String username, MultipartFile file, String plTitle, String plText, String enTitle, String enText, String deTitle, String deText) {
        userService.checkIfUserHasRequiredPermissions(username, Role.ADMIN);

        Article article = new Article();
        article.setPlTitle(plTitle);
        article.setPlText(plText);
        article.setEnTitle(enTitle);
        article.setEnText(enText);
        article.setDeTitle(deTitle);
        article.setDeText(deText);

        String filename = generateFileName();
        filename += getFileType(file.getOriginalFilename());

        if (!file.isEmpty()) {
            String uploadsDir = "/uploads/";
            String realPathToUploads = request.getServletContext().getRealPath(uploadsDir);
            System.out.println(realPathToUploads);
            if (!new File(realPathToUploads).exists()) {
                new File(realPathToUploads).mkdir();
            }

            String filePath = realPathToUploads + filename;
            File dest = new File(filePath);

            try {
                file.transferTo(dest);
                article.setImageUrl(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        articleRepository.save(article);
    }

    @Override
    public Collection<NewestArticlesResponse> getThreeNewestArticles(String language) {
        ArrayList<Article> allArticles = (ArrayList<Article>) articleRepository.findAll();
        ArrayList<NewestArticlesResponse> newestArticles;

        Collections.reverse(allArticles);

        int articlesToGet;

        if (allArticles.size() < 3) {
            articlesToGet = allArticles.size();
        } else {
            articlesToGet = 3;
        }

        newestArticles = getProperArticleData(language, articlesToGet, allArticles);


        return newestArticles;
    }

    private String generateFileName() {

        return UUID.randomUUID().toString().replace("-", "");
    }

    private String getFileType(String fileName) {
        return (fileName.substring(fileName.lastIndexOf(".")).trim());
    }

    private ArrayList<NewestArticlesResponse> getProperArticleData(String language, int articlesToGet, ArrayList<Article> allArticles) {
        ArrayList<NewestArticlesResponse> newestArticles = new ArrayList<>();

        switch (language) {
            case "pl":
                for (int i = 0; i < articlesToGet; i++) {
                    NewestArticlesResponse newestArticlesResponse = new NewestArticlesResponse();
                    newestArticlesResponse.setTitle(allArticles.get(i).getPlTitle());
                    newestArticlesResponse.setText(allArticles.get(i).getPlText());
                    newestArticlesResponse.setImageUrl(allArticles.get(i).getImageUrl());

                    newestArticles.add(newestArticlesResponse);
                }
                break;

            case "en":
                addEnglishArticle(articlesToGet, allArticles, newestArticles);
                break;

            case "de":
                for (int i = 0; i < articlesToGet; i++) {
                    NewestArticlesResponse newestArticlesResponse = new NewestArticlesResponse();
                    newestArticlesResponse.setTitle(allArticles.get(i).getDeTitle());
                    newestArticlesResponse.setText(allArticles.get(i).getDeText());
                    newestArticlesResponse.setImageUrl(allArticles.get(i).getImageUrl());

                    newestArticles.add(newestArticlesResponse);
                }
                break;

            default:
                addEnglishArticle(articlesToGet, allArticles, newestArticles);
        }

        return newestArticles;
    }

    private void addEnglishArticle(int articlesToGet, ArrayList<Article> allArticles, ArrayList<NewestArticlesResponse> newestArticles) {
        for (int i = 0; i < articlesToGet; i++) {
            NewestArticlesResponse newestArticlesResponse = new NewestArticlesResponse();
            newestArticlesResponse.setTitle(allArticles.get(i).getEnTitle());
            newestArticlesResponse.setText(allArticles.get(i).getEnText());
            newestArticlesResponse.setImageUrl(allArticles.get(i).getImageUrl());

            newestArticles.add(newestArticlesResponse);
        }
    }
}
