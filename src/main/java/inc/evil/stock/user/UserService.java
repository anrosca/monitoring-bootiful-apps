package inc.evil.stock.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        log.debug("Finding all users");
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User findById(String id) {
        log.debug("Finding user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user with id: '" + id + "' was found."));
    }

    @Transactional
    public User create(User user) {
        log.debug("Creating user: {}", user);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User findByUserName(String userName) {
        log.debug("Finding user with username: '{}'", userName);
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("No user with userName: '" + userName + "' was found."));
    }
}
