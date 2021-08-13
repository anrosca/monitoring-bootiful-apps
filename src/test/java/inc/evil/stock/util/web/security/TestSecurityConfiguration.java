package inc.evil.stock.util.web.security;

import inc.evil.stock.config.security.StockProfitTrackerUserDetailsService;
import inc.evil.stock.config.security.SecurityUtil;
import inc.evil.stock.user.Email;
import inc.evil.stock.user.UserDto;
import inc.evil.stock.user.UserFacade;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.Mockito.when;

@TestConfiguration
public class TestSecurityConfiguration {
    public static final String USER_ID = "5284f3d2-92d6-438a-8e24-949bd074662a";
    public static final String USER_NAME = "mike";
    public static final String USER_PASSWORD = "shiba";

    @Bean
    public StockProfitTrackerUserDetailsService userDetailsService() {
        StockProfitTrackerUserDetailsService userDetailsService = Mockito.mock(StockProfitTrackerUserDetailsService.class);
        UserDetails userDetails = User.withUsername(USER_NAME)
                .password(USER_PASSWORD)
                .authorities("read", "write")
                .build();
        when(userDetailsService.loadUserByUsername("mike")).thenReturn(userDetails);
        return userDetailsService;
    }

    @Bean
    public UserFacade userFacade() {
        UserFacade userFacade = Mockito.mock(UserFacade.class);
        inc.evil.stock.user.User user = inc.evil.stock.user.User.builder()
                .id(USER_ID)
                .firstName("Mike")
                .lastName("Smith")
                .userName(USER_NAME)
                .email(new Email("pikey@gmail.com"))
                .password(USER_PASSWORD)
                .build();
        when(userFacade.findByUserName(USER_NAME)).thenReturn(UserDto.from(user));
        return userFacade;
    }

    @Bean
    public SecurityUtil securityUtil(UserFacade userFacade) {
        return new SecurityUtil(userFacade);
    }
}
