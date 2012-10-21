package csd.massemailer;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.*;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import com.wiser.WiserLibrary;

public class WiserLibraryTestCase {

	private Wiser mockWiser;
	private WiserLibrary lib;

	@Before
	public void setUp() {
		mockWiser = mock(Wiser.class);
		lib = new WiserLibrary(mockWiser);
	}

	@Test
	public void testStart() {
		lib.startWiser();
		verify(mockWiser).setPort(2525);
		verify(mockWiser).start();
	}

	@Test
	public void testStop() {
		lib.stopWiser();
		verify(mockWiser).stop();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckMailRecievedWrongRecipient() {
		WiserMessage mockMsg = mock(WiserMessage.class);			
		when(mockMsg.getEnvelopeReceiver()).thenReturn("wrong recipient");
		ArrayList<WiserMessage> messages = new ArrayList<WiserMessage>();
		messages.add(mockMsg);				
		when(mockWiser.getMessages()).thenReturn(messages);
		lib.checkEmail("recipients", "subject", "body");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckMailNotRecievedAnyEmail() {
		ArrayList<WiserMessage> messages = new ArrayList<WiserMessage>();		
		when(mockWiser.getMessages()).thenReturn(messages);
		lib.checkEmail("recipients", "subject", "body");		
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCheckMailNullReceivedMails() {
		when(mockWiser.getMessages()).thenReturn(null);
		lib.checkEmail("recipients", "subject", "body");
		verify(mockWiser).getMessages();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void throwUpIfMoreThanOneMessageReceived() throws Exception {
		WiserMessage msg1 = mock(WiserMessage.class);
		WiserMessage msg2 = mock(WiserMessage.class);
		List<WiserMessage> twoMessages = Arrays.asList(msg1, msg2);
		when(mockWiser.getMessages()).thenReturn(twoMessages);
		
		lib.checkEmail("rec1", "subjec1", "body1");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckMailRecievedWrongSubject() throws Exception {
		WiserMessage mockMsg = mock(WiserMessage.class);			
		MimeMessage mockMimeMessage = mock(MimeMessage.class);
		when(mockMsg.getMimeMessage()).thenReturn(mockMimeMessage);
		when(mockMimeMessage.getSubject()).thenReturn("wrong subject");		
		ArrayList<WiserMessage> messages = new ArrayList<WiserMessage>();
		messages.add(mockMsg);				
		when(mockWiser.getMessages()).thenReturn(messages);
		lib.checkEmail("recipients", "subject", "body");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCheckMailReceivedWrongBody() throws Exception {
		WiserMessage mockMsg = mock(WiserMessage.class);		
		when(mockMsg.getEnvelopeReceiver()).thenReturn("recipient");
		MimeMessage mockMimeMessage = mock(MimeMessage.class);		
		when(mockMsg.getMimeMessage()).thenReturn(mockMimeMessage);		
		when(mockMimeMessage.getSubject()).thenReturn("subject");
		when(mockMimeMessage.getContent()).thenReturn("wrong content");		
		ArrayList<WiserMessage> messages = new ArrayList<WiserMessage>();
		messages.add(mockMsg);				
		when(mockWiser.getMessages()).thenReturn(messages);
		lib.checkEmail("recipient", "subject", "content");
	}
}
