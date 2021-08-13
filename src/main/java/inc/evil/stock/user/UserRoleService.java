package inc.evil.stock.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService {
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional(readOnly = true)
    public List<UserRole> findRolesForUserName(String userName) {
        return userRoleRepository.findRolesForUserName(userName);
    }
}
