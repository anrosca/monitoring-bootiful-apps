package inc.evil.stock.user;

import javax.validation.constraints.NotNull;

public class CreateUserDto {
    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    public CreateUserDto() {
    }

    public CreateUserDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

    public User toUser() {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
