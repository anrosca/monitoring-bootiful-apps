package inc.evil.stock.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

@Embeddable
public class Email {
    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    protected Email() {
    }

    public Email(String emailAddress) {
        this.emailAddress = requireNonNull(emailAddress, "Email address must not be null");
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String email) {
        this.emailAddress = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return emailAddress.equals(email1.emailAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress);
    }

    @Override
    public String toString() {
        return emailAddress;
    }
}
