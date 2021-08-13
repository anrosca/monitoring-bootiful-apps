package inc.evil.stock.user;

public class UserRoleDto {
    private String roleName;
    private String userId;

    public UserRoleDto() {
    }

    private UserRoleDto(UserRoleDtoBuilder builder) {
        this.roleName = builder.roleName;
        this.userId = builder.userId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static UserRoleDtoBuilder builder() {
        return new UserRoleDtoBuilder();
    }

    public static UserRoleDto from(UserRole userRole) {
        return UserRoleDto.builder()
                .roleName(userRole.getRoleName())
                .userId(userRole.getUser().getId())
                .build();
    }

    public static class UserRoleDtoBuilder {
        private String roleName;
        private String userId;

        public UserRoleDtoBuilder roleName(String roleName) {
            this.roleName = roleName;
            return this;
        }

        public UserRoleDtoBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public UserRoleDto build() {
            return new UserRoleDto(this);
        }
    }
}
