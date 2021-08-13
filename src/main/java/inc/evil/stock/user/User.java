package inc.evil.stock.user;

import inc.evil.stock.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Column(name = "password", nullable = false)
    private String password;

    private Email email;

    protected User() {
    }

    private User(UserBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.password = builder.password;
        this.enabled = builder.enabled;
        this.email = builder.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public static UserBuilder builder() {
        return new UserBuilder();
    }

    public static class UserBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String userName;
        private boolean enabled = true;
        private String password;
        private Email email;

        public UserBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder enabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public UserBuilder email(Email email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
