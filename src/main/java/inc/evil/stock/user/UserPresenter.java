package inc.evil.stock.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Component;

@Component
public class UserPresenter {
    private final UserFacade userFacade;
    private final UserModelAssembler userModelAssembler;

    public UserPresenter(UserFacade userFacade, UserModelAssembler userModelAssembler) {
        this.userFacade = userFacade;
        this.userModelAssembler = userModelAssembler;
    }

    public CollectionModel<EntityModel<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> users = userFacade.findAll(pageable);
        return userModelAssembler.toPagedModel(users);
    }

    public EntityModel<UserDto> findById(String id) {
        UserDto user = userFacade.findById(id);
        return userModelAssembler.toModel(user);
    }

    public UserDto create(CreateUserDto createUserDto) {
        return userFacade.create(createUserDto);
    }
}
