package cz.codecamp.lunchbitch.models;

public class Email {

    private final String emailAddress;

    public Email(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public static Email of(String emailAddress) {
        return new Email(emailAddress);
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;

        return emailAddress != null ? emailAddress.equals(email.emailAddress) : email.emailAddress == null;

    }

    @Override
    public int hashCode() {
        return emailAddress != null ? emailAddress.hashCode() : 0;
    }
}
