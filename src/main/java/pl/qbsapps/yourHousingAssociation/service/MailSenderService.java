package pl.qbsapps.yourHousingAssociation.service;

public interface MailSenderService {
    void sendEmail(String receiver, String content);
    String createEmailContent(String welcome, String description, String confirmation, String token);
}
