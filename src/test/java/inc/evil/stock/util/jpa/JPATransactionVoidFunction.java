package inc.evil.stock.util.jpa;

import java.util.function.Consumer;
import javax.persistence.EntityManager;

/**
 * @author Vlad Mihalcea
 */
@FunctionalInterface
public interface JPATransactionVoidFunction extends Consumer<EntityManager> {
    default void beforeTransactionCompletion() {
    }

    default void afterTransactionCompletion() {
    }
}
