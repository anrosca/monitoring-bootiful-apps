package inc.evil.stock.user;

import inc.evil.stock.entity.AbstractEntity;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;

@Entity
@Table(name = "user_roles", indexes = @Index(name = "unique_user_roles", columnList = "id, role_name"))
public class UserRole extends AbstractEntity {
    public static final Comparator<UserRole> ROLE_NAME_COMPARATOR = Comparator.comparing(UserRole::getRoleName);

    @Column(name = "role_name", nullable = false)
    private String roleName;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    protected UserRole() {
    }

    private UserRole(UserRoleBuilder builder) {
        this.roleName = builder.roleName;
        this.user = builder.user;
        this.id = builder.id;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public static UserRoleBuilder builder() {
        return new UserRoleBuilder();
    }

    public static class UserRoleBuilder {
        private String id;
        private String roleName;
        private User user;
        private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);
        private OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);

        public UserRoleBuilder id(String id) {
            this.id = id;
            return this;
        }

        public UserRoleBuilder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public UserRoleBuilder user(User user) {
            this.user = user;
            return this;
        }

        public UserRoleBuilder createdAt(OffsetDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public UserRoleBuilder updatedAt(OffsetDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public UserRole build() {
            return new UserRole(this);
        }
    }
}
