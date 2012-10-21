package csd.massemailer;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.*;

import csd.massemailer.model.MassEmailerMessage;

public class MassEmailerMessageTest {
	@Test
	public void testEmailValidation() {
		List<String> oneEmail = Arrays.asList("stanly@odd-e.com");
		MassEmailerMessage message = new MassEmailerMessage(oneEmail, "aSubject", "aContent");
		assertTrue(message.isValid());
	}

	@Test
	public void testEmailWithoutSubjectValidation() {
		List<String> oneEmail = Arrays.asList("stanly2@odd-e.com");
		MassEmailerMessage message = new MassEmailerMessage(oneEmail, "", "aContent");
		assertFalse(message.isValid());
	}

	@Test
	public void testEmailWithoutEmailAlertMessageValidation() {
		List<String> noEmail = Arrays.asList();
		MassEmailerMessage message = new MassEmailerMessage(noEmail, "aSubject", "aContent");
		assertFalse(message.isValid());
		assertEquals("Email is required", message.getErrorMessage());
	}
	
	@Test
	public void testEmailWithMultipleRecipientsErrorInOneAlertMessageValidation() {
		String wrongEmail = "stan6@seven";
		List<String> twoEmails = Arrays.asList("stan4@five.com", wrongEmail);
		MassEmailerMessage message = new MassEmailerMessage(twoEmails,"aSubject", "aContent");
		assertFalse(message.isValid());
		assertEquals("Email format error for " + wrongEmail, message.getErrorMessage());
	}
	
	@Test
	public void testRecipientsAsStringConstructorInvalidRecipients() {
		MassEmailerMessage message = new MassEmailerMessage("","aSubject", "aContent");
		assertFalse(message.isValid());
	}
	
	@Test
	public void testRecipientsAsStringConstructorOneValidRecipient() {
		MassEmailerMessage message = new MassEmailerMessage("recipient1@example.org","aSubject", "aContent");
		assertTrue(message.isValid());
	}
	
	@Test
	public void testRecipientsAsStringConstructorTwoValidRecipients() {
		MassEmailerMessage message = new MassEmailerMessage("recipient1@example.org,recipient2@example.org","aSubject", "aContent");
		assertTrue(message.isValid());
	}
}
