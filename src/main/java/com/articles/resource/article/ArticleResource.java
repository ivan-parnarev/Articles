package com.articles.resource.article;

import com.articles.data.model.Article;
import com.articles.service.article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ArticleResource {

    private final ArticleMapper articleMapper;
    private final ArticleService articleService;

    @Autowired
    ArticleResource(ArticleMapper articleMapper, ArticleService articleService) {
        this.articleMapper = articleMapper;
        this.articleService = articleService;
    }

    @PostMapping("/articles")
    ResponseEntity<String> postArticles(@RequestBody ArticlesDto articlesDto) {
        List<Article> articles = articleMapper.map(articlesDto);
        articleService.save(articlesDto.getUserID(), articles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/articles")
    ResponseEntity<String> updateArticles(@RequestBody ArticlesDto articlesDto) {
        List<Article> articles = articleMapper.map(articlesDto);
        articleService.update(articlesDto.getUserID(), articles);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/categories/top-three")
    ResponseEntity<String> getTopThreeCategoriesForUser(@RequestParam String userId) {
        List<String> topArticles = articleService.getThreeTopCategories(userId);
        return new ResponseEntity<>(topArticles.toString(), HttpStatus.OK);
    }
}
