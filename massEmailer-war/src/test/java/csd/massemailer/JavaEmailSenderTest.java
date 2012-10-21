package csd.massemailer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import csd.massemailer.service.SmtpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import csd.massemailer.model.MassEmailerMessage;
import csd.massemailer.service.JavaEmailSender;


public class JavaEmailSenderTest {
	MassEmailerMessage message;
	List<MimeMessage> mimeMessages ;
	JavaEmailSender javaMailSender;
    private SmtpServer mockSmtpServer;

    @Before
	public void beforeTest(){
		List<String> oneEmail = Arrays.asList("stanly24@odd-e.com");
		message = new MassEmailerMessage(oneEmail, "aSubject","aContent");
		mimeMessages = new LinkedList<MimeMessage>();
        mockSmtpServer = mock(SmtpServer.class);
		javaMailSender = new JavaEmailSender(mockSmtpServer);
	}

	@Test
	public void whenComposingMimeMessageTheRecipientShouldBeWhatWasIntended() throws Exception {
		javaMailSender.sendMail(message);
        verify(mockSmtpServer).send(message.getRecipients().get(0), message.getSubject(), message.getBody());
        verifyNoMoreInteractions(mockSmtpServer);
	}

	@Test
	public void shouldSendTwoMessages() throws Exception{
		List<String> twoEmail = Arrays.asList("stanly2@odd-e.com", "asdas@sdfs.com");
		MassEmailerMessage message = new MassEmailerMessage(twoEmail,"aSubject","aContent");
		
		javaMailSender.sendMail(message);

        verify(mockSmtpServer).send(message.getRecipients().get(0), message.getSubject(), message.getBody());
        verify(mockSmtpServer).send(message.getRecipients().get(1), message.getSubject(), message.getBody());
        verifyNoMoreInteractions(mockSmtpServer);
	}
}
