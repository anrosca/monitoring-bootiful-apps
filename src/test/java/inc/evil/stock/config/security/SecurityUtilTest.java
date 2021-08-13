package inc.evil.stock.config.security;

import inc.evil.stock.user.UserDto;
import inc.evil.stock.user.UserFacade;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class SecurityUtilTest {
    private static final String AUTHENTICATED_USER_NAME = "mike";

    private final UserFacade userFacade = Mockito.mock(UserFacade.class);
    private final SecurityUtil securityUtil = new SecurityUtil(userFacade);

    @Test
    public void shouldBeAbleToGetCurrentlyAuthenticatedUser() {
        UserDto expectedUser = UserDto.builder()
                .userName(AUTHENTICATED_USER_NAME)
                .firstName("Mike")
                .lastName("Smith")
                .id("1111-2222-3333-4444")
                .build();
        Mockito.when(userFacade.findByUserName(AUTHENTICATED_USER_NAME)).thenReturn(expectedUser);
        setUpSecurityContext(expectedUser);

        UserDto actualUser = securityUtil.getAuthenticatedUser();

        assertThat(actualUser.getUserName()).isEqualTo(AUTHENTICATED_USER_NAME);
    }

    @Test
    public void shouldThrowUsernameNotFoundException_whenSecurityContextIsEmpty() {
        setUpEmptySecurityContext();

        assertThatThrownBy(securityUtil::getAuthenticatedUser).hasMessage("No Authentication in current SecurityContext");
    }

    private void setUpSecurityContext(UserDto user) {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password("password")
                .authorities(List.of(new SimpleGrantedAuthority("read")))
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, "password", userDetails.getAuthorities());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);
    }

    private void setUpEmptySecurityContext() {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(context);
    }
}
