package inc.evil.stock.config.env;

import org.springframework.boot.diagnostics.AbstractFailureAnalyzer;
import org.springframework.boot.diagnostics.FailureAnalysis;

public class NoActiveProfilesSetFailureAnalyzer extends AbstractFailureAnalyzer<NoActiveProfilesSetException> {

    @Override
    protected FailureAnalysis analyze(Throwable rootFailure, NoActiveProfilesSetException cause) {
        String description = "Not even a single spring profile was set. " +
                "You must specify at least one spring profile in order to run the app.";
        String action = "Consider adding a profile, like 'dev', 'test' or 'docker' like this: \n" +
                "java -jar <<app-name>>.jar --spring.profiles.active=<<profile-name>>\n\n" +
                "For example, for development use java -jar stock-profit-tracker-0.0.0-SNAPSHOT.jar --spring.profiles.active=dev\n";
        return new FailureAnalysis(description, action, cause);
    }
}
