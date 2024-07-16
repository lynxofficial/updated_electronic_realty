package ru.realty.erealty.service.image;

public interface ImageHandlingService<T, E> {

    T attachImage(E messageHelper);
}
