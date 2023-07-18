package com.articles.service.article;

import com.articles.data.model.Article;
import com.articles.data.persistence.SimpleDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    private final SimpleDatabase simpleDatabase;

    @Autowired
    public ArticleService(SimpleDatabase simpleDatabase) {
        this.simpleDatabase = simpleDatabase;
    }

    public void save(String userID, List<Article> articles) {
        try {
            simpleDatabase.save(userID, articles);
        } catch (Exception e) {
            throw new ArticleServiceException("Could not create articles", e);
        }
    }

    public void update(String userID, List<Article> articles) {
        try {
            simpleDatabase.update(userID, articles);
        } catch (Exception e) {
            throw new ArticleServiceException("Could not update articles", e);
        }
    }

    public List<String> getThreeTopCategories(String userID) {
        try {
            return simpleDatabase.getTopThreeCategoriesForUser(userID);
        } catch (Exception e) {
            throw new ArticleServiceException("Could not get top three categories", e);
        }
    }
}
