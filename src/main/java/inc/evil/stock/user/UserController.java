package inc.evil.stock.user;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserPresenter userPresenter;

    public UserController(UserPresenter userPresenter) {
        this.userPresenter = userPresenter;
    }

    @GetMapping
    public CollectionModel<EntityModel<UserDto>> findAll(@PageableDefault Pageable pageable) {
        return userPresenter.findAll(pageable);
    }

    @GetMapping("{id}")
    public EntityModel<UserDto> findById(@PathVariable("id") String id) {
        return userPresenter.findById(id);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateUserDto createUserDto) {
        UserDto createdUser = userPresenter.create(createUserDto);
        URI location = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass())
                        .findById(createdUser.getId()))
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
