package inc.evil.stock.user;

import inc.evil.stock.util.json.JsonSerde;
import inc.evil.stock.util.web.ResponseBodyMatchers;
import inc.evil.stock.util.web.security.TestSecurityConfiguration;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = TestSecurityConfiguration.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserPresenter userPresenter;

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void whenThereAreNoUsers_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(List.of()));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToGetAllUsers() throws Exception {
        User user = User.builder()
                .id("1111-2222-3333-4444")
                .userName("msmith")
                .firstName("Mike")
                .email(new Email("pikey@gmail.com"))
                .lastName("Smith")
                .build();
        List<UserDto> expectedUsers = List.of(UserDto.from(user));
        CollectionModel<EntityModel<UserDto>> usersModel = CollectionModel.wrap(expectedUsers);
        when(userPresenter.findAll(any(Pageable.class))).thenReturn(usersModel);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsListAsJson(expectedUsers, "users"));
    }

    @Test
    @WithMockUser(TestSecurityConfiguration.USER_NAME)
    public void shouldBeAbleToGetUserById() throws Exception {
        String userId = "5412f3d2-11f3-231a-5d24-34dfd074666d";
        UserDto expectedUser = UserDto.builder()
                .id(userId)
                .firstName("Mike")
                .lastName("Smith")
                .build();
        when(userPresenter.findById(userId)).thenReturn(EntityModel.of(expectedUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andExpect(ResponseBodyMatchers.responseBody().containsObjectAsJson(expectedUser, UserDto.class));
    }

    @Test
    public void shouldBeAbleToCreateUsers() throws Exception {
        UserDto user = UserDto.builder()
                .id("5412f3d2-11f3-231a-5d24-34dfd074666d")
                .firstName("Satoshi")
                .lastName("Nakamoto")
                .build();
        when(userPresenter.create(any())).thenReturn(user);
        CreateUserDto requestPayload = new CreateUserDto("Satoshi", "Nakamoto");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/").contentType(MediaType.APPLICATION_JSON).content(JsonSerde.toJson(requestPayload)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.endsWith("/api/v1/users/5412f3d2-11f3-231a-5d24-34dfd074666d")));
    }
}
