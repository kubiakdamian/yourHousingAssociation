package pl.qbsapps.yourHousingAssociation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ComponentScan({"pl.qbsapps.yourHousingAssociation.config"})
@PropertySource("classpath:application.properties")
public class MailSenderConfig {

    private final Environment environment;

    @Autowired
    public MailSenderConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public JavaMailSenderImpl mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(environment.getRequiredProperty("spring.mail.host"));
        mailSender.setPort(Integer.valueOf(environment.getRequiredProperty("spring.mail.port")));

        mailSender.setUsername(environment.getRequiredProperty("spring.mail.username"));
        mailSender.setPassword(environment.getRequiredProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", environment.getRequiredProperty("spring.mail.transport.protocol"));
        props.put("mail.smtp.auth", environment.getRequiredProperty("spring.mail.properties.mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", environment.getRequiredProperty("spring.mail.properties.mail.smtp.starttls.enable"));
        props.put("mail.smtp.starttls.required", environment.getRequiredProperty("spring.mail.properties.mail.smtp.starttls.required"));

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }
}
