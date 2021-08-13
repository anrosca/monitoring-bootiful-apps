package inc.evil.stock.util.jpa;

import java.util.function.Function;
import javax.persistence.EntityManager;

/**
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface JPATransactionFunction<T> extends Function<EntityManager, T> {
    default void beforeTransactionCompletion() {
    }
    default void afterTransactionCompletion() {
    }
}
