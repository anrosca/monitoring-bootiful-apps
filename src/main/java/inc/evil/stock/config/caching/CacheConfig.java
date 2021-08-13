package inc.evil.stock.config.caching;

public class CacheConfig {
    private final int entryTtlMinutes;
    private final boolean enabled;

    public CacheConfig(int entryTtlMinutes, boolean enabled) {
        this.entryTtlMinutes = entryTtlMinutes;
        this.enabled = enabled;
    }

    public int getEntryTtlMinutes() {
        return entryTtlMinutes;
    }

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "CacheConfig{" +
                "entryTtlMinutes=" + entryTtlMinutes +
                ", enabled=" + enabled +
                '}';
    }
}
