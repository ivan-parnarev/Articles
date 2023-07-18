package com.articles.data.persistence;

import com.articles.data.model.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SimpleDatabase {

    private static final Map<String, List<Article>> DATABASE = new HashMap<>();

    public void save(String id, List<Article> data) {
        if (DATABASE.containsKey(id)) {
            throw new InsertRowException("Could not insert row");
        }
        DATABASE.put(id, data);
    }

    public void update(String id, List<Article> data) {
        if (exists(id)) {
            DATABASE.put(id, data);
        } else {
            throw new NoRowException("ID not found");
        }
    }

    public List<Article> getById(String id) {
        if (!exists(id)) {
            throw new NoRowException("Could not select row");
        }
        return DATABASE.get(id);
    }

    public List<String> getTopThreeCategoriesForUser(String userId) {
        List<Article> userLikedArticles = getById(userId);

        // Filter liked articles
        List<Article> likedArticles = new ArrayList<>();
        for (Article article : userLikedArticles) {
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

    public boolean exists(String id) {
        return DATABASE.containsKey(id);
    }
}
