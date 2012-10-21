package csd.massemailer;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import csd.massemailer.service.*;

public class AddRecipientTestCase {

	AddRecipientController addRecipientController = new AddRecipientController();
	
	String MODELVIEW_SUCCESS_VAR = "addRecipientResult";
	ModelAndView mv;
	
	@Test
	public void shouldreturnfailWhenLastNameAndFirstNameAreEmpty() throws Exception{
		mv = addRecipientController.addRecipient("", "", "aaaa@sss.com");
		assertFalse(getModelSuccessValue());
	}

	@Test
	public void shouldAddRecipientWhenFirstNameOrLastNameAndEmailIsValid() throws Exception {
		mv = addRecipientController.addRecipient("steve","", "test@abc.com");
		assertTrue(getModelSuccessValue());
	}
	
	@Test
	public void shouldNotAddRecipientWhenEmailIsNotValid() throws Exception
	{
		mv = addRecipientController.addRecipient("aaa", "adasd", "dads@.com");
		assertFalse(getModelSuccessValue());
	}

	@Test
	public void shouldNotAddRecipientToSystemIfDuplicateEmailIsFound() throws Exception {
		mv = addRecipientController.addRecipient("steve", "jobs", "steve.jobs@apple.com");
		mv = addRecipientController.addRecipient("steve", "jobs", "steve.jobs@apple.com");
		assertFalse(getModelSuccessValue());
	}
	private Boolean getModelSuccessValue() {
		return (Boolean) mv.getModel().get(MODELVIEW_SUCCESS_VAR);
	}
	
}
