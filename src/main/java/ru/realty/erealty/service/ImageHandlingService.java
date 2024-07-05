package ru.realty.erealty.service;

public interface ImageHandlingService<T, E> {

    T attachImage(E messageHelper);
}
