package com.articles.resource.exception_handler;

import com.articles.service.article.ArticleServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        logger(e);

        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ArticleServiceException.class)
    public ResponseEntity<String> handleArticleException(Exception e) {
        logger(e);

        return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
    }

    private void logger(Exception e) {
        e.printStackTrace();
    }
}
