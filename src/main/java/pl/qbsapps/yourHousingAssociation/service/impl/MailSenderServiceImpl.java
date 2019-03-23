package pl.qbsapps.yourHousingAssociation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.qbsapps.yourHousingAssociation.service.MailSenderService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderServiceImpl implements MailSenderService {

    private final static String SUBJECT = "yourHousingAssociation - Link aktywacyjny";

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String mailSender;

    @Autowired
    public MailSenderServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendEmail(String receiver, String content) {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(receiver);
            helper.setFrom(mailSender);
            helper.setSubject(SUBJECT);
            helper.setText(content, true);
            javaMailSender.send(mail);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public String createEmailContent(String welcome, String description, String confirmation, String token) {
        Context context = new Context();
        context.setVariable("welcome", welcome);
        context.setVariable("description", description);
        context.setVariable("confirmation", confirmation);
        context.setVariable("token", token);

        return templateEngine.process("UserActivationMail", context);
    }
}
