package com.example.course_paper_backend.services.mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class MailService {

    private static final String signature = "Пожалуйста не отвечайте на это письмо, так как оно автоматически отправлено с сервера.";
    private static final String fromEmail = "a.oshroev.07@mail.ru";
    private static final String passwordTest = "YanpU7ka9xm5MptcNPTB";

    private static Session getSession(){
        String host = "smtp.mail.ru";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "false");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.port", 465);
        props.put("mail.debug", "true");
        props.put("mail.smtp.password", passwordTest);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.mail.ru");
        props.put("mail.user", fromEmail);
        props.put("mail.password", passwordTest);


        // Получение объекта Session по умолчанию
        return Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication  getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                fromEmail, passwordTest);
                    }
                });
    }

    public static void sentMessage(String to, String subject, String text){
                try {
            // Создание объекта MimeMessage по умолчанию
            MimeMessage message = new MimeMessage(getSession());

            // Установить От: поле заголовка
            message.setFrom(new InternetAddress(fromEmail));

            // Установить Кому: поле заголовка
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Установить тему: поле заголовка
            message.setSubject(subject);

            // Теперь установите фактическое сообщение
            message.setText(text + "\n\n" + signature);

            // Отправить сообщение
            Transport.send(message);
            System.out.println("Сообщение успешно отправлено....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
