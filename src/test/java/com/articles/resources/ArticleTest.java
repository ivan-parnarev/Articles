package com.articles.resources;

import com.articles.data.model.Article;
import com.articles.data.persistence.InsufficientInformationException;
import com.articles.resource.article.ArticlesDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.articles.resource.article.ArticleDto.ArticleDtoBuilder.newArticleDtoBuilder;
import static com.articles.resource.article.ArticlesDto.ArticlesDtoBuilder.newArticlesDtoBuilder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.TestDataSupport.createArticles;
import static util.TestDataSupport.createRequestData;
import static util.TestSupportRandom.randomBoolean;
import static util.TestSupportRandom.randomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleTest {

    @LocalServerPort
    private int port;
    private final String user = "user";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldPostArticles() {
        String url = String.format("http://localhost:%d/api/v1/articles", port);

        var articleDto = newArticleDtoBuilder()
                .withTitle(randomString())
                .withCategory(randomString())
                .withLike(randomBoolean())
                .build();

        var articlesDto = newArticlesDtoBuilder()
                .withUserID(randomString())
                .withArticleDtos(List.of(articleDto))
                .build();

        var actual = testRestTemplate.postForEntity(url, articlesDto, String.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    void shouldReturnBadRequestWhenArticlesAlreadyStoredAgainstUserID() {
        String url = String.format("http://localhost:%d/api/v1/articles", port);

        var articleDto = newArticleDtoBuilder()
                .withTitle(randomString())
                .withCategory(randomString())
                .withLike(randomBoolean())
                .build();

        var articlesDto = newArticlesDtoBuilder()
                .withUserID(randomString())
                .withArticleDtos(List.of(articleDto))
                .build();

        var response = testRestTemplate.postForEntity(url, articlesDto, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));

        var actual = testRestTemplate.postForEntity(url, articlesDto, String.class);
        assertThat(actual.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    void shouldReturnTopThreeCategoriesForTheUser() {
        String postUrl = String.format("http://localhost:%d/api/v1/articles", port);
        ArticlesDto articles = createRequestData();
        List<Article> listOfArticles = createArticles();

        List<String> topThreeCategories = getTopThreeCategoriesFromList(listOfArticles);

        String getUrl = String.format("http://localhost:%d/api/v1/categories/top-three?userId=%s", port, articles.getUserID());

        var created = testRestTemplate.postForEntity(postUrl, articles, String.class);
        assertThat(created.getStatusCode(), is(HttpStatus.CREATED));

        var expected = testRestTemplate.getForEntity(getUrl, String.class);
        assertThat(expected.getStatusCode(), is(HttpStatus.OK));
        assertEquals(expected.getBody(), topThreeCategories.toString());
    }

    private static List<String> getTopThreeCategoriesFromList(List<Article> list) {
        // Filter liked articles
        List<Article> likedArticles = new ArrayList<>();
        for (Article article : list) {
            if (article.isLike()) {
                likedArticles.add(article);
            }
        }

        // Count categories and store in a map
        Map<String, Integer> categoryCountMap = new HashMap<>();
        for (Article article : likedArticles) {
            String category = article.getCategory();
            categoryCountMap.put(category, categoryCountMap.getOrDefault(category, 0) + 1);
        }

        if (categoryCountMap.size() < 3) {
            throw new InsufficientInformationException("Need more than 2 categories to use this functionality");
        }

        // Sort categories based on the number of likes in descending order
        List<Map.Entry<String, Integer>> sortedCategories =
                new ArrayList<>(categoryCountMap.entrySet());
        sortedCategories.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // Get the top three categories
        List<String> topThreeCategories = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sortedCategories.size()); i++) {
            topThreeCategories.add(sortedCategories.get(i).getKey());
        }
        return topThreeCategories;
    }
}