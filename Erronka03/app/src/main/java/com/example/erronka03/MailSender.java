package com.example.erronka03;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
    private final String senderEmail = "zbrstudios41@gmail.com"; // Tu correo
    private final String senderPassword = "dnvt cciv elir qhbu ";  // Usa una contraseña de aplicación

    public void sendMail(String recipientEmail, String subject, String messageBody) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);
            message.setText(messageBody);

            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
