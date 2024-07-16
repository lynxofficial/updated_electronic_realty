package ru.realty.erealty.service.mail;

import ru.realty.erealty.entity.User;

public interface MailSendingService {

    void sendEmail(User user, String url);

    String sendEmail(User user);
}
