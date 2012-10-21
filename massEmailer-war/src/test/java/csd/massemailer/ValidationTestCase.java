package csd.massemailer;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import csd.massemailer.model.Recipient;
import csd.massemailer.utils.CommonMsg;
import csd.massemailer.utils.Validation;


public class ValidationTestCase {
	
	private Validation validator = new Validation();  
	
	@Test
	public void emailShouldHaveAtSymbol() {
		assertFalse(validator.validateEmailFormat("abcdef.com")); 
	}
	
	@Test
	public void emailShouldHaveName() throws Exception {
		assertFalse(validator.validateEmailFormat("@aaa.com"));
	}
	
	@Test
	public void emailShoulHaveDomain() throws Exception {
		assertFalse(validator.validateEmailFormat("steven@aa.com.aa!!!"));
	}
	
	@Test
	public void emailIsValid() throws Exception {
		assertTrue(validator.validateEmailFormat("stevejob@apple.com"));
	}
	
	@Test
	public void nameShouldHaveFirstNameOrLastName() throws Exception {
		String firstName = "";
		String lastName = "";
		assertEquals(Recipient.validateName(firstName,lastName), CommonMsg.NAME_MISSING_BOTH_FIRST_LAST_NAME);
	}

	@Test
	public void eitherFirstNameOrLastNameShouldBeGiven() throws Exception {
		String firstName = "";
		String lastName = "Jobs";
		assertEquals(Recipient.validateName(firstName, lastName), CommonMsg.NAME_SUCCESS);				
	}
	
	@Test
	public void shouldNotAddDuplicatedRecipientToDatabase() throws Exception
	{
		List<Recipient> recipientList = new ArrayList<Recipient>();
		Recipient recipient = new Recipient("aaa", "", "jobs@apple.com");
		recipientList.add(recipient);
		assertTrue(validator.isRecipientDuplicate(recipientList, recipient ));
	}
	
}
