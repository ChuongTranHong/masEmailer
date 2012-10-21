package csd.massemailer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csd.massemailer.model.Recipient;
import csd.massemailer.service.StorageProvider;
import csd.massemailer.utils.CommonMsg;
import csd.massemailer.utils.Validation;


@Controller
@RequestMapping ("/recipient")
@Scope(value="session")
public class AddRecipientController {
	
	private StorageProvider storageProvider = new StorageProvider() {

		private List<Recipient> recipients = new ArrayList<Recipient>(); 
			
		public boolean addRecipient(Recipient tobeAdded) {
			recipients.add(tobeAdded);
			return true;
		}

		public List<Recipient> getRecipientList() {
			return this.recipients;
		}
		
	};
	private Validation validator = new Validation();
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView getAddRecipientPage()
	{
		return new ModelAndView("addrecipient");
	}

	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ModelAndView addRecipient(@RequestParam (value="firstName") String firstName, 
									 @RequestParam (value= "lastName") String lastName, 
									 @RequestParam (value="email") String email) {
		
		List<Recipient> recipients = null;
		try {
			recipients = (ArrayList<Recipient>)storageProvider.getRecipientList();
		} catch (IOException ioe) {
			return createModelAndView(false, CommonMsg.STORAGE_READ_ERROR);
		}
		Recipient recipient = new Recipient(firstName, lastName, email);
		
		if(!CommonMsg.NAME_SUCCESS.equals(Recipient.validateName(firstName, lastName)))
			return createModelAndView(false,CommonMsg.NAME_MISSING_BOTH_FIRST_LAST_NAME);
		if(!validator.validateEmailFormat(email))
			return createModelAndView(false,CommonMsg.EMAIL_FAIL);
		if(validator.isRecipientDuplicate(recipients, recipient))
			return createModelAndView(false,CommonMsg.RECIPIENT_DUPLICATED);
		
		recipients.add(recipient);
		return createModelAndView(true,CommonMsg.RECIPIENT_ADD_SUCCESS);
	}

	protected ModelAndView createModelAndView(Boolean addRecipientSuccess, 	String addRecipientResultMessage) {
		ModelAndView mv = new ModelAndView("addrecipient");
		mv.getModel().put("addRecipientResult",addRecipientSuccess);
		mv.getModel().put("addRecipientResultMessage", addRecipientResultMessage);
		return mv;
	}

}
