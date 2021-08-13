package inc.evil.stock.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    @Query("select ur from UserRole ur where ur.user.userName = :userName")
    List<UserRole> findRolesForUserName(@Param("userName") String userName);
}
