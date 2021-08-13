package inc.evil.stock.config.security;

import inc.evil.stock.user.User;
import inc.evil.stock.user.UserRoleDto;
import inc.evil.stock.user.UserRoleFacade;
import inc.evil.stock.user.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StockProfitTrackerUserDetailsService implements UserDetailsService {
    private final UserService userService;
    private final UserRoleFacade userRoleFacade;

    public StockProfitTrackerUserDetailsService(UserService userService, UserRoleFacade userRoleFacade) {
        this.userService = userService;
        this.userRoleFacade = userRoleFacade;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findByUserName(userName);
        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(user.getPassword())
                .authorities(getAuthorities(userName))
                .build();
    }

    private Collection<SimpleGrantedAuthority> getAuthorities(String userName) {
        return userRoleFacade.findRolesForUserName(userName)
                .stream()
                .map(UserRoleDto::getRoleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
