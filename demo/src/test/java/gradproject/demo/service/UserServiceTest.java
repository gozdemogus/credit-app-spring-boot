package gradproject.demo.service;

import gradproject.demo.entity.User;
import gradproject.demo.exception.UserNotFoundException;
import gradproject.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    @InjectMocks
    private UserService mockUserService;

    @Test
    void getAllUsers() {
        List<User> userList = new ArrayList<>();
        userList.add(customUser());
        userList.add(customUser());

        when(mockUserRepository.findAll()).thenReturn(userList);

        assertEquals(userList.size(), mockUserService.getAllUsers().size());
        verify(mockUserRepository).findAll();
    }

    @Test
    void findUserByIdentityNumber() {
        User user = customUser();

        Mockito.when(mockUserRepository.findUserByIdentityNumber(user.getIdentityNumber())).thenReturn(user);
        User userFound = mockUserService.findUserByIdentityNumber(user.getIdentityNumber());

        assertEquals(user,userFound);
        verify(mockUserRepository).findUserByIdentityNumber(user.getIdentityNumber());
    }

    @Test
    void not_findUserByIdentityNumber(){
        when(mockUserRepository.findUserByIdentityNumber(00000000000L)).thenThrow(new NotFoundException("User Not Found."));

        assertThrows(NotFoundException.class, () -> mockUserService.findUserByIdentityNumber(00000000000L));

        verify(mockUserRepository).findUserByIdentityNumber(00000000000L);
    }

    @Test
    void saveUser() {
        User user = customUser();

        when(mockUserRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        User userSaved = mockUserService.saveUser(user);

        assertEquals(user, userSaved);
        verify(mockUserRepository).save(user);
    }

    @Test
    void deleteUserByIdentity() {
        User user = customUser();

        when(mockUserRepository.findUserByIdentityNumber(user.getIdentityNumber())).thenReturn(user);

        mockUserService.deleteUserByIdentity(user.getIdentityNumber());
        verify(mockUserRepository).delete(user);
    }

    @Test
    void not_deleteUserByIdentity() {

        when(mockUserRepository.findUserByIdentityNumber(00000000000L)).thenThrow(new UserNotFoundException("User Not Found."));

        assertThrows(UserNotFoundException.class, () -> mockUserService.deleteUserByIdentity(00000000000L));

        verify(mockUserRepository).findUserByIdentityNumber(00000000000L);
    }

    private User customUser() {
        User user = new User();
        user.setId(1L);
        user.setIdentityNumber(26335477280L);
        user.setName("Gözdem");
        user.setSurname("Oğuş");
        user.setSalary(10000);
        user.setPhoneNumber("5345648897");
        return user;
    }

}