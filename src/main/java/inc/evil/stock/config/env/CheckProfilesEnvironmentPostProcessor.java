package inc.evil.stock.config.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

public class CheckProfilesEnvironmentPostProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (environment.getActiveProfiles().length == 0) {
            throw new NoActiveProfilesSetException("Not even a single spring profile was set");
        }
    }
}
