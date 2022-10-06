import com.revature.exception.*;
import com.revature.model.AppReimbursementTicket;
import com.revature.repository.AppTicketRepository;
import com.revature.service.AppTicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class AppTicketServiceTest {
    @Mock
    private AppTicketRepository ticketRepository;

    @InjectMocks
    private AppTicketService ticketService;

    /****   Testing the processReimbursementTicket method in AppTicketService   ****/


    // Negative test - the ticket to process does not exist in database
    // the ReimbursementTicketNotFoundException
    @Test
    public void testReimbursementTicketExistsIsFalse() throws SQLException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();

        // mocking a response from ticketRepo
        // this method returns true if the ticket exists, false if not
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(false);

        // note that the if statement in ticketService we're testing the exception throw
        // not ! the result, so if no ticket found (false) -> (true) to throw the exception

        // Act + ASSERT
        Assertions.assertThrows(ReimbursementTicketNotFoundException.class, ()->{
            ticketService.processReimbursementTicket(ticket);
        });
    }

    // Negative test - the ticket exists, BUT the ticket has been processed (No longer PENDING)
    // the ProcessedReimbursementTicketException
    @Test
    public void testReimbursementTicketIsProcessedTrue() throws SQLException, ProcessedReimbursementTicketException {
        // Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();

        // mocking a response from ticketRepo
        // Let's have this say that the ticket does exist to move on to the next test
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(true);
        // this method returns true if the ticket is processed, else false
        when(ticketRepository.checkReimbursementTicketAlreadyProcessed(eq(ticket))).thenReturn(true);

        // Act + ASSERT
        Assertions.assertThrows(ProcessedReimbursementTicketException.class, ()->{
            ticketService.processReimbursementTicket(ticket);
        });
    }

    // Negative test - the ticket exists and PENDING, BUT the ticket status to update is not APPROVED 2 or DENIED 3
    // the InvalidReimbursementTicketStatusUpdateException
    @Test
    public void testReimbursementTicketStatusUpdate() throws SQLException, InvalidReimbursementTicketStatusUpdateException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setTicketStatus(4);

        // mocking a response from ticketRepo
        // Let's have this say that the ticket does exist to move on to the next test
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(true);
        // Let's have this say that the ticket is not processed before to move on to the next test
        when(ticketRepository.checkReimbursementTicketAlreadyProcessed(eq(ticket))).thenReturn(false);

        // Act + ASSERT
        Assertions.assertThrows(InvalidReimbursementTicketStatusUpdateException.class, ()->{
            ticketService.processReimbursementTicket(ticket);
        });
    }

    // Negative test - the ticket exists, status update valid, but no managerId to tell who processed it
    // will realistically not happen because the controller handles it and user needs to be logged in but just to be sure
    // another InvalidReimbursementTicketStatusUpdateException
    @Test
    public void testMissingManagerIdOnTicketUpdate() throws SQLException, InvalidReimbursementTicketStatusUpdateException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setTicketStatus(2);  // set this to a valid status to move forward in method
        ticket.setManagerId(0); // there is no such user with id 0 in database

        // mocking a response from ticketRepo
        // Let's have this say that the ticket does exist to move on to the next test
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(true);
        // Let's have this say that the ticket is not processed before to move on to the next test
        when(ticketRepository.checkReimbursementTicketAlreadyProcessed(eq(ticket))).thenReturn(false);


        // Act + ASSERT
        Assertions.assertThrows(InvalidReimbursementTicketStatusUpdateException.class, ()->{
            ticketService.processReimbursementTicket(ticket);
        });
    }

    // Positive test - ticketRepository.processReimbursementTicket() gets invoked
    @Test
    public void testInvokeProcessTicketForUpdate() throws SQLException, ProcessedReimbursementTicketException, InvalidReimbursementTicketStatusUpdateException, ReimbursementTicketNotFoundException {
        // Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setTicketStatus(2);  // set this to a valid status to move forward in method
        ticket.setManagerId(1); // there is no such user with id 0 in database

        // mocking a response from usersRepo
        // Let's have this say that the ticket does exist to move on to the next test
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(true);
        // Let's have this say that the ticket is not processed before to move on to the next test
        when(ticketRepository.checkReimbursementTicketAlreadyProcessed(eq(ticket))).thenReturn(false);


        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.processReimbursementTicket() is triggered, we use doNothing() from Mockito
        // because processReimbursementTicket() is a void method
        doNothing().when(ticketRepository).processReimbursementTicket(isA(AppReimbursementTicket.class));

        // Although processReimbursementTicket() does nothing
        // we can check if it was invoked by using verify()
        ticketService.processReimbursementTicket(ticket);
        verify(ticketRepository, times(1)).processReimbursementTicket(ticket);
    }

    // Positive test - ticketRepository.processReimbursementTicket() gets invoked
    // Part 2 of testing method
    @Test
    public void testCheckObjectInInvokeProcessTicketForUpdate() throws SQLException, ProcessedReimbursementTicketException, InvalidReimbursementTicketStatusUpdateException, ReimbursementTicketNotFoundException {
        // Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setTicketStatus(2);  // set this to a valid status to move forward in method
        ticket.setManagerId(1); // there is no such user with id 0 in database

        // While using the doNothing(), we can capture the argument by using a ArgumentCaptor too
        ArgumentCaptor<AppReimbursementTicket> valueCapture = ArgumentCaptor.forClass(AppReimbursementTicket.class);

        // mocking a response from usersRepo
        // Let's have this say that the ticket does exist to move on to the next test
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(true);
        // Let's have this say that the ticket is not processed before to move on to the next test
        when(ticketRepository.checkReimbursementTicketAlreadyProcessed(eq(ticket))).thenReturn(false);


        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.processReimbursementTicket() is triggered, we use doNothing() from Mockito
        // because processReimbursementTicket() is a void method, and capture the passed in ReimbursementTicket object
        doNothing().when(ticketRepository).processReimbursementTicket(valueCapture.capture());

        // Although processReimbursementTicket() does nothing
        // we can check if the passed in ReimbursementTicket object in ticketService.processReimbursementTicket()
        // matches when it gets passed into the invoke method for ticketService.processReimbursementTicket()
        ticketService.processReimbursementTicket(ticket);
        Assertions.assertEquals(ticket, valueCapture.getValue());
    }


    /****   Testing the submitReimbursementTicket method in AppTicketService   ****/

    // Negative test - the submitted reimbursement ticket does not have a valid amount
    // the InvalidReimbursementTicketSubmissionException
    @Test
    public void testSubmitReimbursementTicketAmount() throws SQLException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(-1.50);    // amount <= 0.00 is an invalid amount

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidReimbursementTicketSubmissionException.class,
                ()->{ ticketService.submitReimbursementTicket(ticket); },
                "A reimbursement ticket must have a value greater then 0.00"
        );
    }

    // Negative test - the submitted reimbursement ticket does not have a valid description
    // in this case, description is blank
    // the InvalidReimbursementTicketSubmissionException
    @Test
    public void testSubmitReimbursementTicketDescriptionIsBlank() throws SQLException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(50.50);    // amount <= 0.00 is an invalid amount
        ticket.setDescription("");  // empty string description is invalid

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidReimbursementTicketSubmissionException.class,
            ()->{ ticketService.submitReimbursementTicket(ticket); },
            "A reimbursement ticket must have a description as for why"
        );
    }

    // Negative test #2 - the submitted reimbursement ticket does not have a valid description
    // in this case, description is null
    // the NullPointerException - this is the expected exception because we can't get the value of a null
    @Test
    public void testSubmitReimbursementTicketDescriptionIsNull() throws SQLException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(50.50);    // amount <= 0.00 is an invalid amount
        // not setting the description makes it null, so it's an invalid value

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(NullPointerException.class,
                ()->{ ticketService.submitReimbursementTicket(ticket); }
        );
    }

    // Negative test - the submitted reimbursement ticket does not have a valid employeeId
    // meaning a 'valid' user didn't make it (this is taken care of in AuthController b/c user is logged in at this point)
    // the InvalidReimbursementTicketSubmissionException
    @Test
    public void testSubmitReimbursementTicketEmployeeIdIsValid() throws SQLException {
        //Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(50.50);    // amount <= 0.00 is an invalid amount
        ticket.setDescription("this is a test");  // empty string description is invalid
        ticket.setEmployeeId(0);    // there is no way a User in our database can have an id of <=0 because of SERIAL at table creation

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(InvalidReimbursementTicketSubmissionException.class,
                ()->{ ticketService.submitReimbursementTicket(ticket); },
                "A reimbursement ticket must have the employee's id number"
        );
    }

    // Positive test - ticketRepository.submitReimbursementTicket() gets invoked
    @Test
    public void testInvokeSubmitTicketForUpdate() throws SQLException, InvalidReimbursementTicketSubmissionException {
        // Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(100.50);
        ticket.setDescription("this is a test");
        ticket.setEmployeeId(2);

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.processReimbursementTicket() is triggered, we use doNothing() from Mockito
        // because processReimbursementTicket() is a void method
        doNothing().when(ticketRepository).submitReimbursementTicket(isA(AppReimbursementTicket.class));

        // Although processReimbursementTicket() does nothing
        // we can check if it was invoked by using verify()
        ticketService.submitReimbursementTicket(ticket);
        verify(ticketRepository, times(1)).submitReimbursementTicket(ticket);
    }

    // Positive test #2 - ticketRepository.submitReimbursementTicket() gets invoked
    // Part 2 of testing method
    @Test
    public void testCheckObjectInInvokeSubmitTicketForCreatation() throws SQLException, InvalidReimbursementTicketSubmissionException {
        // Arrange
        AppReimbursementTicket ticket = new AppReimbursementTicket();
        ticket.setAmount(100.50);   // a valid amount is > 0
        ticket.setDescription("this is a test"); // a valid description is not empty
        ticket.setEmployeeId(2);    // valid employeeId in our database is >0 (because of SERIAL at creation)

        // While using the doNothing(), we can capture the argument by using a ArgumentCaptor too
        ArgumentCaptor<AppReimbursementTicket> valueCapture = ArgumentCaptor.forClass(AppReimbursementTicket.class);

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.submitReimbursementTicket() is triggered, we use doNothing() from Mockito
        // because submitReimbursementTicket() is a void method, and capture the passed in ReimbursementTicket object
        doNothing().when(ticketRepository).submitReimbursementTicket(valueCapture.capture());

        // Although submitReimbursementTicket() does nothing
        // we can check if the passed in ReimbursementTicket object in ticketService.submitReimbursementTicket()
        // matches when it gets passed into the invoke method for ticketService.submitReimbursementTicket()
        ticketService.submitReimbursementTicket(ticket);
        Assertions.assertEquals(ticket, valueCapture.getValue());
    }


    /****   Testing the getAllTicket method in AppTicketService   ****/

    // Positive test - ticketService.getALlTickets() gets invoked & returns a List
    @Test
    public void testInvokeGetAllTickets() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer

        List<AppReimbursementTicket> ticketList = new ArrayList<>(); // fake list to return

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.getAllTickets() is triggered, return a AppReimbursementTicket List
        // because getAllTickets() returns a list
        when(ticketRepository.getAllTickets()).thenReturn(ticketList);

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // want to check that the object List is expected
        Assertions.assertEquals(ticketList, ticketService.getAllTickets());
    }


    /****   Testing the getAllTicketForEmployee method in AppTicketService   ****/

    // Positive test - ticketService.getALlTicketsForEmployee() gets invoked & returns a List
    @Test
    public void testGetAllTicketsForEmployee() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer

        List<AppReimbursementTicket> ticketList = new ArrayList<>(); // fake list to return

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.getAllTickets() is triggered, return a AppReimbursementTicket List
        // because getAllTickets() returns a list
        when(ticketRepository.getAllTicketsForEmployee(isA(Integer.class))).thenReturn(ticketList);

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // want to check that the object List is expected
        Assertions.assertEquals(ticketList, ticketService.getAllTicketsForEmployee(2));
    }

    /****   Testing the getAllTicketsWithStatus method in AppTicketService   ****/

    // Negative test - ticketService.getAllTicketsWithStatus() gets invoked & throws exception
    @Test
    public void testGetAllTicketsWithStatusException() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer
        int ticketStatus = 0;   // invalid status, throws exception

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ ticketService.getAllTicketsWithStatus(ticketStatus); },
                "The reimbursement tickets should status: PENDING (1), APPROVED (2), or DENIED (3)"
        );
    }

    // Positive test - ticketService.getAllTicketsWithStatus() gets invoked & returns a List
    @Test
    public void testGetAllTicketsWithStatusExecutes() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer

        int ticketStatus = 1;   // valid status, throws exception
        List<AppReimbursementTicket> ticketList = new ArrayList<>(); // fake list to return

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.getAllTicketsWithStatus() is triggered, return a AppReimbursementTicket List
        // because getAllTicketsWithStatus() returns a list
        when(ticketRepository.getAllTicketsWithStatus(isA(Integer.class))).thenReturn(ticketList);

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // want to check that the object List is expected
        Assertions.assertEquals(ticketList, ticketService.getAllTicketsWithStatus(1));
    }

    /****   Testing the getAllTicketsWithStatusForEmployee method in AppTicketService   ****/

    // Negative test - ticketService.getAllTicketsWithStatusForEmployee() gets invoked & throws exception
    @Test
    public void testGetAllTicketsWithStatusForEmployeeException() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer
        int ticketStatus = 0;   // invalid status, throws exception

        // Act + ASSERT

        // assertThrows(Class<T> expectedType, Executable executable, String message)
        // Asserts that execution of the supplied executable throws an exception of the expectedType and returns the exception.
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{ ticketService.getAllTicketsWithStatus(ticketStatus); },
                "The reimbursement tickets should status: PENDING (1), APPROVED (2), or DENIED (3)"
        );
    }

    // Positive test - ticketService.getAllTicketsWithStatusForEmployee() gets invoked & returns a List
    @Test
    public void testGetAllTicketsWithStatusForEmployeeExecutes() throws SQLException {
        // Arrange - assume you are a valid employee/manager from controller layer

        int ticketStatus = 1;   // valid status, throws exception
        List<AppReimbursementTicket> ticketList = new ArrayList<>(); // fake list to return

        // Act + ASSERT

        // mocking a response from ticketRepo
        // when ticketRepository.getAllTicketsWithStatusForEmployee() is triggered, return a AppReimbursementTicket List
        // because getAllTicketsWithStatusForEmployee() returns a list
        when(ticketRepository.getAllTicketsWithStatusForEmployee(isA(Integer.class), isA(Integer.class))).thenReturn(ticketList);

        // assertEquals(Object expected, Object actual)
        // Asserts that expected and actual are equal.
        // want to check that the object List is expected
        Assertions.assertEquals(ticketList, ticketService.getAllTicketsWithStatusForEmployee(1, ticketStatus));
    }
}
