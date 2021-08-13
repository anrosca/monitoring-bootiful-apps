package inc.evil.stock.config.caching;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Map;

@ConfigurationProperties("caching")
@Validated
public class CachingProperties {
    /**
     * Indicated whether caching is enabled(true) of disabled(false) globally
     */
    private final boolean enabled;

    /**
     * Default TTL for a cache entry
     */
    private final int defaultEntryTtlMinutes;

    /**
     * Cache-specific configurations
     */
    @ValidCacheName
    private final Map<String, CacheConfig> configuredCaches;

    @ConstructorBinding
    public CachingProperties(boolean enabled, int defaultEntryTtlMinutes, Map<String, CacheConfig> configuredCaches) {
        this.enabled = enabled;
        this.defaultEntryTtlMinutes = defaultEntryTtlMinutes;
        this.configuredCaches = Collections.unmodifiableMap(configuredCaches);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Map<String, CacheConfig> getConfiguredCaches() {
        return configuredCaches;
    }

    public int getDefaultEntryTtlMinutes() {
        return defaultEntryTtlMinutes;
    }

    @Override
    public String toString() {
        return "CachingProperties{" +
                "enabled=" + enabled +
                ", defaultEntryTtlMinutes=" + defaultEntryTtlMinutes +
                ", configuredCaches=" + configuredCaches +
                '}';
    }
}
