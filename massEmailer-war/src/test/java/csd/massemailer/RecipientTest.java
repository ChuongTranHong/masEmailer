package csd.massemailer;

import org.junit.*;

import csd.massemailer.model.Recipient;
import csd.massemailer.utils.CommonMsg;
import static org.junit.Assert.*;


public class RecipientTest {
	@Test
	public void canHaveOnlyLastName(){
		Recipient recipient = new Recipient("", "aLastName", "any@Email.org");
		assertTrue(recipient.isValid());
	}
	
	@Test
	public void canHaveOnlyFirstName(){
		Recipient recipient = new Recipient("aFirstName", "", "any@Email.org");
		assertTrue(recipient.isValid());
	}
	
	@Test
	public void shouldHaveEitherFirstNameOrLastName(){
		Recipient recipient = new Recipient("", "", "any@Email.org");
		assertFalse(recipient.isValid());
		assertEquals(CommonMsg.NAME_MISSING_BOTH_FIRST_LAST_NAME, recipient.getErrorMessage());
	}
	
	@Test
	public void shouldHaveEmailAddress() {
		Recipient recipient = new Recipient("", "aLastName", "");
		assertFalse(recipient.isValid());
		assertEquals(CommonMsg.EMAIL_FAIL, recipient.getErrorMessage());
	}
	
	@Test
	public void emailAddressShouldBeValid() {
		Recipient recipient = new Recipient("", "aLastName", "asdfjb");
		assertFalse(recipient.isValid());
		assertEquals(CommonMsg.EMAIL_FAIL, recipient.getErrorMessage());
	}
	
	@Test
	public void toStringShouldReturnCorrectFormat(){
		Recipient recipient = new Recipient("aFirstName", "aLastName", "any@Email.org");
		assertEquals("any@Email.org,aFirstName,aLastName",recipient.toString());
	}
}
