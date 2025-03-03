

import jakarta.mail.internet.MimeMessage;
import org.example.bookingnotifier.service.EmailService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.*;

public class EmailServiceTest {

    private final JavaMailSender mailSender = mock(JavaMailSender.class);
    private final EmailService emailService = new EmailService(mailSender);

    @Test
    public void testSendEmail() throws Exception {
        // Create a real MimeMessage instance instead of mocking
        MimeMessage mimeMessage = new MimeMessage((jakarta.mail.Session) null);

        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Call the send email function
        emailService.sendEmail("testuser@gmail.com", "Test Subject", "Test Body");

        // Verify mailSender.send() is called
        verify(mailSender, times(1)).send(mimeMessage);
    }
}
