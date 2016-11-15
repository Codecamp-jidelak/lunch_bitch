package cz.codecamp.lunchbitch.services.emailService;

import cz.codecamp.lunchbitch.models.LunchMenuDemand;
import cz.codecamp.lunchbitch.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import java.time.LocalDate;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private final Message message;

    @Autowired
    public EmailServiceImpl(Message message) {
        this.message = message;
    }


    public void sendEmailsToSubscribers(List<LunchMenuDemand> lunchMenuDemandList) throws MessagingException {
        for(LunchMenuDemand lunchMenu : lunchMenuDemandList){
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(lunchMenu.getEmail()));
            message.setContent(buildLunchMenuList(lunchMenu.getRestaurants()), "text/html; charset=utf-8");
            Transport.send(message);
        }
    }

    private String buildLunchMenuList(List<Restaurant> restaurants) throws MessagingException {
        StringBuilder builder = new StringBuilder();
        builder.append(
                "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//CS\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                        "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                        " <head>\n" +
                        "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                        "  <title>Demystifying Email Design</title>\n" +
                        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
                        "</head>\n" +
                        "<body>" +
                        "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\" width=\"50%\">" +
                        "<tr>" +
                        "<td align=\"center\" bgcolor=\"#ffffff\" style=\"padding: 40px 0 30px 0;\">\n" +
                        "<h1 style=\"color:black;\">" + "Pošli jídelak ;-)" + "</h1>" +
                        "<p>" + LocalDate.now() + "</p>" +
                        " <img src=\"https://pbs.twimg.com/profile_images/668088709875499008/eLZTn7_S.jpg\" alt=\"Pošli jídelák\" style=\"display: block;\" />\n" +
                        "</td>" +
                        "</tr>");
        for(Restaurant restaurant: restaurants){
            builder.append(restaurant.toString());
        }
        return builder.append("</table>").append("</body>").append("</html>").toString();
    }

}
