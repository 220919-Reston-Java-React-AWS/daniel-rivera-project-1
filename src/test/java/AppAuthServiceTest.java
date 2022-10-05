import com.revature.exception.InvalidAccountLoginException;
import com.revature.model.AppUserAccount;
import com.revature.repository.AppUsersRepository;
import com.revature.service.AppAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AppAuthServiceTest {
    @Mock
    private AppUsersRepository usersRepository;

    @InjectMocks
    private AppAuthService authService;

    /****   Testing the LOGIN method cases in AppAuthService    ****/

    // Negative test - incorrect username login
    @Test
    public void testIncorrectUsernameAccountLogin() throws SQLException, InvalidAccountLoginException {
        // Arrange

        // mocking a response from usersRepo
        when(usersRepository.getUserByUsernameAndPassword(eq("mike.roger"), eq("password123"))).thenReturn(null);

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidAccountLoginException.class,
            ()->{ authService.login("mike.roger", "password123"); },
            "Invalid username and/or password"
        );
    }

    // Negative test - incorrect password
    @Test
    public void testIncorrectPasswordAccountLogin() throws SQLException, InvalidAccountLoginException {
        // Arrange

        // mocking a response from usersRepo
        when(usersRepository.getUserByUsernameAndPassword(eq("mike.rogers"), eq("123"))).thenReturn(null);

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidAccountLoginException.class,
                ()->{ authService.login("mike.rogers", "123"); },
            "Invalid username and/or password"
        );
    }

    // Positive Test - username and password are correct, a user got returned
    @Test
    public void testCorrectUsernamePasswordAccountLogin() throws SQLException, InvalidAccountLoginException {
        // Arrange

        // mocking a response from usersRepo
        // when getUserBYUsernameAndPassword is triggered, return an UserAccount object
        when(usersRepository.getUserByUsernameAndPassword(eq("mike.rogers"), eq("password123"))).thenReturn(new AppUserAccount(1, "mike.rogers", "password123", "mike", "rogers", 1));

        // Act + ASSERT

        // assertNotNull(Object actual)
        // Asserts that actual is not null.
        // Assertions.assertNotNull(authService.login("mike.rogers", "password123"));

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // Assertions.assertEquals(new AppUserAccount(1, "mike.rogers", "password123", "mike", "rogers", 1), authService.login("mike.rogers", "password123"));

        // To run multiple Assertions without ending the test early and missing assertions, we use ...
        // assertAll(Executable... executables)
        // Asserts that all supplied executables do not throw exceptions.
        Assertions.assertAll(
                () -> Assertions.assertNotNull(authService.login("mike.rogers", "password123")),
                () -> Assertions.assertEquals(new AppUserAccount(1, "mike.rogers", "password123", "mike", "rogers", 1), authService.login("mike.rogers", "password123"))
        );
    }

}
