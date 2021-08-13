package inc.evil.stock.user;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto fromEntity(User user) {
        return UserDto.from(user);
    }

    public List<UserDto> fromEntity(List<User> users) {
        return users.stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public Page<UserDto> fromEntity(Page<User> users) {
        return users.map(UserDto::from);
    }
}
