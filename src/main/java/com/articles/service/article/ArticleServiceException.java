package com.articles.service.article;

public class ArticleServiceException extends RuntimeException {
    public ArticleServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
