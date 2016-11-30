package cz.codecamp.lunchbitch.webPageMappers;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Filip Ocelka on 19.11.2016, filip.ocelka@gmail.com
 */
public class EmailForm {

    @Email
    @NotEmpty
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailForm() {
    }

    public EmailForm(String email) {
        this.email = email;
    }
}
