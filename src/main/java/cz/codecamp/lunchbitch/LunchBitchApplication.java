package cz.codecamp.lunchbitch;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.services.webService.WebService;
import cz.codecamp.lunchbitch.services.webService.WebServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Logger;

@SpringBootApplication
@Configuration
public class LunchBitchApplication {

    @Value("${email.from}")
    private String email;

    @Value("${password}")
    private String password;

    @Value("${zomato.key}")
    private String zomatoId;

    public static void main(String[] args) {
        SpringApplication.run(LunchBitchApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Logger logger() {
        return Logger.getLogger("LunchBitch");
    }

    @Bean
    public Properties properties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        return properties;
    }

    @Bean
    public Message getMessage(Session session) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(email));
        message.setSubject("Pošli jídelák: " + new Date());
        return message;
    }

    @Bean
    public Session getSession(Properties properties) {

        return Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                });
    }

    @Bean
    @Qualifier("google")
    public HttpHeaders httpGoogleHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Bean
    @Qualifier("google")
    public HttpEntity<String> httpGoogleEntity(@Qualifier("google") HttpHeaders headers){
        return new HttpEntity<>("parameters", headers);
    }

    @Bean
    @Qualifier("zomato")
    public HttpHeaders httpZomatoHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("user-key", zomatoId);
        return headers;
    }

    @Bean
    @Qualifier("zomato")
    public HttpEntity<String> httpZomatoEntity(@Qualifier("zomato") HttpHeaders headers){
        return new HttpEntity<>("parameters", headers);
    }

    @Bean
    public TemplateEngine templateEngine(ITemplateResolver templateResolver){
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(templateResolver);
        return templateEngine;
    }


    @Bean
    public ITemplateResolver templateResolver(){
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(1));
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setPrefix("/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    @Bean
    public WebService webService() {
        return new WebServiceImpl(new LunchMenuDemand(), new LunchMenuDemand());
    }
}
