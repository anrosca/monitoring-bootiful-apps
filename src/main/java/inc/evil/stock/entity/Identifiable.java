package inc.evil.stock.entity;

import java.io.Serializable;

public interface Identifiable<T extends Serializable> {
    T getId();
}
