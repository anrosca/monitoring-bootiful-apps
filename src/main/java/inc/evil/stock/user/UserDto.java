package inc.evil.stock.user;

import org.springframework.hateoas.server.core.Relation;

@Relation(itemRelation = "user", collectionRelation = "users")
public class UserDto {
    private String id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;

    public UserDto() {
    }

    private UserDto(UserDtoBuilder builder) {
        this.id = builder.id;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.userName = builder.userName;
        this.email = builder.email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDto from(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .email(user.getEmail().getEmailAddress())
                .build();
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public static UserDtoBuilder builder() {
        return new UserDtoBuilder();
    }

    public static class UserDtoBuilder {
        private String id;
        private String firstName;
        private String lastName;
        private String userName;
        private String email;

        public UserDtoBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserDtoBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDtoBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDtoBuilder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserDtoBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserDto build() {
            return new UserDto(this);
        }
    }
}
