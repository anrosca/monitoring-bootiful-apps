package inc.evil.stock.user;

import inc.evil.stock.config.caching.CacheNames;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@CacheConfig(cacheNames = CacheNames.USER_CACHE)
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserFacade(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    public Page<UserDto> findAll(Pageable pageable) {
        Page<User> users = userService.findAll(pageable);
        return userMapper.fromEntity(users);
    }

    @Cacheable
    public UserDto findById(String id) {
        User user = userService.findById(id);
        return userMapper.fromEntity(user);
    }

    @CachePut(key = "#result.id")
    public UserDto create(CreateUserDto createUserDto) {
        User user = userService.create(createUserDto.toUser());
        return userMapper.fromEntity(user);
    }

    @Cacheable
    public UserDto findByUserName(String userName) {
        User user = userService.findByUserName(userName);
        return userMapper.fromEntity(user);
    }
}
