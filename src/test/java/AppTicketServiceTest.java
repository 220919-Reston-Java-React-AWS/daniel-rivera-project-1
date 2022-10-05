import com.revature.exception.ReimbursementTicketNotFoundException;
import com.revature.model.AppReimbursementTicket;
import com.revature.repository.AppTicketRepository;
import com.revature.service.AppTicketService;
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
public class AppTicketServiceTest {
    @Mock
    private AppTicketRepository ticketRepository;

    @InjectMocks
    private AppTicketService ticketService;

    // Negative test - incorrect username login
    @Test
    public void testReimbursementTicketExistsIsFalse() throws SQLException {
        //
        AppReimbursementTicket ticket = new AppReimbursementTicket();

        // mocking a response from usersRepo
        when(ticketRepository.checkReimbursementTicketExist(eq(ticket))).thenReturn(false);

        // Act + ASSERT
        Assertions.assertThrows(ReimbursementTicketNotFoundException.class, ()->{
            ticketService.processReimbursementTicket(ticket);
        });
    }
}
