package inc.evil.stock.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@MappedSuperclass
public class AbstractEntity implements Identifiable<String> {
    @Id
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "UUID")
    protected String id;

    @Column(name = "created_at", nullable = false)
    protected OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    @Column(name = "updated_at", nullable = false)
    protected OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);

    public String getId() {
        return id;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AbstractEntity))
            return false;
        AbstractEntity other = (AbstractEntity) o;
        return Objects.equals(getId(), other.getId());
    }

    public int hashCode() {
        return getClass().hashCode();
    }
}
