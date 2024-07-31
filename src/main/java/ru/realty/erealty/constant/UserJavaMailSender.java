package ru.realty.erealty.constant;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class UserJavaMailSender {
    public static final JavaMailSender DEFAULT_JAVA_MAIL_SENDER = new JavaMailSenderImpl();
}
