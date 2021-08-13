package inc.evil.stock.config.caching;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidCacheNameConstraintValidator.class)
public @interface ValidCacheName {
    String message() default "{inc.evil.stock.config.caching.ValidCacheName}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
