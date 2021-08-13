package inc.evil.stock.config.security;

import inc.evil.stock.user.UserDto;
import inc.evil.stock.user.UserFacade;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private final UserFacade userFacade;

    public SecurityUtil(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    public UserDto getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("No Authentication in current SecurityContext");
        }
        return userFacade.findByUserName(authentication.getName());
    }
}
