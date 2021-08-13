package inc.evil.stock.user;

import inc.evil.stock.config.caching.CacheNames;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@CacheConfig(cacheNames = CacheNames.USER_ROLES_CACHE)
public class UserRoleFacade {
    private final UserRoleService userRoleService;

    public UserRoleFacade(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @Cacheable
    public List<UserRoleDto> findRolesForUserName(String userName) {
        return userRoleService.findRolesForUserName(userName)
                .stream()
                .map(UserRoleDto::from)
                .collect(Collectors.toList());
    }
}
