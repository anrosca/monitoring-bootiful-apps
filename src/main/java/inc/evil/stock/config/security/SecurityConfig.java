package inc.evil.stock.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    private final StockProfitTrackerUserDetailsService stockProfitTrackerUserDetailsService;
    private final SecurityUtil securityUtil;

    public SecurityConfig(StockProfitTrackerUserDetailsService stockProfitTrackerUserDetailsService, SecurityUtil securityUtil) {
        this.stockProfitTrackerUserDetailsService = stockProfitTrackerUserDetailsService;
        this.securityUtil = securityUtil;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(stockProfitTrackerUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .mvcMatchers("/api/v1/hello").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable();
    }

    @Override
    public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthenticatedUserArgumentResolver(securityUtil));
    }
}
