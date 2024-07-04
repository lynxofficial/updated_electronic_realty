package ru.realty.erealty.service;

import ru.realty.erealty.entity.User;

public interface MailSendingService {

    void sendEmail(User user, String url, String defaultMailImagePath);

    String sendEmail(User user);
}
