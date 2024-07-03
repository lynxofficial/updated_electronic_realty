package ru.realty.erealty.service;

import ru.realty.erealty.entity.User;

import java.util.List;

public interface UserSearchingService {

    List<User> findAll();

    User findByEmail(String email);
}
