package inc.evil.stock.config.caching;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ValidCacheNameConstraintValidator implements ConstraintValidator<ValidCacheName, Map<String, CacheConfig>> {
    @Override
    public boolean isValid(Map<String, CacheConfig> cacheConfigsToValidate, ConstraintValidatorContext constraintValidatorContext) {
        Set<String> validCacheNames = getValidCacheNames();
        if (validCacheNames.isEmpty()) {
            return true;
        }
        for (String cacheNameToValidate : cacheConfigsToValidate.keySet()) {
            boolean isValidCacheName = validCacheNames.contains(cacheNameToValidate);
            if (!isValidCacheName) {
                HibernateConstraintValidatorContext hibernateContext =
                        constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class);
                hibernateContext.disableDefaultConstraintViolation();
                hibernateContext.addExpressionVariable("validCacheNames", validCacheNames.toString())
                        .buildConstraintViolationWithTemplate("The specified cache name '" + cacheNameToValidate + "' is" +
                                " not valid. Must be one of: ${validCacheNames}")
                        .addConstraintViolation();
                return false;
            }
        }
        return true;
    }

    private Set<String> getValidCacheNames() {
        try {
            return tryGetValidCacheNames();
        } catch (IllegalAccessException e) {
            throw new CannotGetSupportedCacheNamesException("Unable to get the supported cache names. Expected " +
                    CacheNames.class.getName() + " to contain public static fields with supported cache names.", e);
        }
    }

    private Set<String> tryGetValidCacheNames() throws IllegalAccessException {
        Set<String> cacheNames = new HashSet<>();
        for (Field field : CacheNames.class.getFields()) {
            String validCacheName = (String) field.get(null);
            cacheNames.add(validCacheName);
        }
        return cacheNames;
    }
}
