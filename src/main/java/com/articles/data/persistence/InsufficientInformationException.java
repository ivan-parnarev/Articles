package com.articles.data.persistence;

public class InsufficientInformationException extends RuntimeException {
    public InsufficientInformationException(String message) {
        super(message);
    }
}