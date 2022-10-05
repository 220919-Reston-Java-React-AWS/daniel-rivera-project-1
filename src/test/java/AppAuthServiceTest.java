import com.revature.exception.InvalidAccountLoginException;
import com.revature.exception.InvalidRegisterAccountException;
import com.revature.model.AppUserAccount;
import com.revature.repository.AppTicketRepository;
import com.revature.repository.AppUsersRepository;
import com.revature.service.AppAuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

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

    // Negative test - incorrect password login
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
        // This is because authService.login() should return a UserAccount

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // Assertions.assertEquals(new AppUserAccount(1, "mike.rogers", "password123", "mike", "rogers", 1), authService.login("mike.rogers", "password123"));
        // this is because usersRepository.getUserByUsernameAndPassword() return a UserAccount object from a valid database query

        // To run multiple Assertions without ending the test early and missing assertions, we use ...
        // assertAll(Executable... executables)
        // Asserts that all supplied executables do not throw exceptions.
        Assertions.assertAll(
                () -> Assertions.assertNotNull(authService.login("mike.rogers", "password123")),
                () -> Assertions.assertEquals(new AppUserAccount(1, "mike.rogers", "password123", "mike", "rogers", 1), authService.login("mike.rogers", "password123"))
        );
    }


    /****   Testing the REGISTER method cases in AppAuthService    ****/

    // Negative test - invalid username, contains spaces in username
    @Test
    public void testSpacesInUsernameForAccountRegistration() throws SQLException, InvalidRegisterAccountException{
        // Arrange
        AppUserAccount newAccount = new AppUserAccount();
        newAccount.setUsername("mike rogers");
        newAccount.setPassword("password123");

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidRegisterAccountException.class,
                ()->{ authService.register(newAccount); },
                "Spaces are not allowed in username or password"
        );
    }

    // Negative test - invalid password, contains spaces in username
    @Test
    public void testSpacesInPasswordForAccountRegistration() throws SQLException, InvalidRegisterAccountException{
        // Arrange
        AppUserAccount newAccount = new AppUserAccount();
        newAccount.setUsername("mike.rogers");
        newAccount.setPassword("password 123");

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidRegisterAccountException.class,
                ()->{ authService.register(newAccount); },
                "Spaces are not allowed in username or password"
        );
    }

    // Negative test - the username has already been taken, a user account exists with that username
    @Test
    public void testAccountWithUsernameExistsForRegistration() throws SQLException, InvalidRegisterAccountException{
        // Arrange
        AppUserAccount newAccount = new AppUserAccount();
        newAccount.setUsername("mike.rogers");
        newAccount.setPassword("password123");

        // Act + ASSERT

        // mocking a response from usersRepo
        // when usersRepository.checkUsernameExists(String) is triggered, return 'true' that username is taken
        when(usersRepository.checkUsernameExists(eq(newAccount.getUsername()))).thenReturn(true);


        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidRegisterAccountException.class,
                ()->{ authService.register(newAccount); },
                "Username (" + newAccount.getUsername() + ") has already been taken."
        );
    }

    // Positive test - assuming exception checks passed, invoke the createUserAccount()
    // more details how we're testing a void method below
    @Test
    public void testInvokeCreateAccountForRegistration() throws SQLException, InvalidRegisterAccountException{
        // Arrange
        AppUserAccount newAccount = new AppUserAccount();
        newAccount.setUsername("mike.rogers");
        newAccount.setPassword("password123");

        // Act + ASSERT

        // mocking a response from usersRepo
        // when usersRepository.createUserAccount(user) is triggered, we use doNothing() from Mockito
        // because createUserAccount() is a void method
        doNothing().when(usersRepository).createUserAccount(isA(AppUserAccount.class));

        // Although createUserAccount() does nothing
        // we can check if it was invoked by using verify()
        authService.register(newAccount);
        verify(usersRepository, times(1)).createUserAccount(newAccount);
    }

    // Positive test - assuming exception checks passed, invoke the createUserAccount()
    // part 2 of testing the void method, want to see the passed in UserAccount details are the same
    // if we do invoke usersRepository.createUserAccount()
    @Test
    public void testCheckCreateAccountObjectForRegistration() throws SQLException, InvalidRegisterAccountException{
        // Arrange
        AppUserAccount newAccount = new AppUserAccount();
        newAccount.setUsername("mike.rogers");
        newAccount.setPassword("password123");

        // While using the doNothing(), we can capture the argument by using a ArgumentCaptor too
        ArgumentCaptor<AppUserAccount> valueCapture = ArgumentCaptor.forClass(AppUserAccount.class);

        // Act + ASSERT

        // mocking a response from usersRepo
        // when usersRepository.createUserAccount(user) is triggered, we use doNothing() from Mockito
        // because createUserAccount() is a void method, and capture the passed in UserAccount object
        doNothing().when(usersRepository).createUserAccount(valueCapture.capture());

        // Although createUserAccount() does nothing
        // we can check if the passed in UserAccount object in authService.register()
        // matches when it gets passed into the invoke method for usersRepository.createUserAccount()
        authService.register(newAccount);
        Assertions.assertEquals(newAccount, valueCapture.getValue());
    }
}
