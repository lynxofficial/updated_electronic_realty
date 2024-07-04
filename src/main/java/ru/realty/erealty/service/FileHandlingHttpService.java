package ru.realty.erealty.service;

import java.util.concurrent.CompletableFuture;

public interface FileHandlingHttpService {

    CompletableFuture<String> downloadImage(String imageLink, String defaultMailImagePath, Integer imageNumber);
}
