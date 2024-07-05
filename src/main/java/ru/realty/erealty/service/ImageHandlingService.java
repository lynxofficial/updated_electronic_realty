package ru.realty.erealty.service;

import org.springframework.beans.factory.annotation.Value;

public interface ImageHandlingService<T, E> {

    T attachImage(E messageHelper, String defaultMailImagePath);
}
