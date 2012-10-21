package csd.massemailer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import csd.massemailer.model.MassEmailerMessage;
import csd.massemailer.service.EmailSender;
import csd.massemailer.utils.CommonMsg;


@Controller
@RequestMapping("/mail")
public class EmailController {

	@Autowired
	private EmailSender mailSender;

	@RequestMapping(value="index", method=RequestMethod.GET)
	public ModelAndView getMeIndex() {
		return new ModelAndView("composemail");
	}

	@RequestMapping(value="send",method=RequestMethod.POST)
	public ModelAndView postComposeForm(@RequestParam("recipients") String recipients, 
										@RequestParam("subject") String subject, 
										@RequestParam("message") String body) throws Exception {
		MassEmailerMessage msg = new MassEmailerMessage(recipients, subject, body);
		if(!msg.isValid()) {
			return createModelAndView(false, msg.getErrorMessage());
		}
		try {
			this.mailSender.sendMail(msg);
			return createModelAndView(true, CommonMsg.EMAIL_SUCCESS_SEND);
		} catch (Exception e) {
			return createModelAndView(false, e.getMessage());
		}
	}

	private ModelAndView createModelAndView(boolean success, String status) {
		ModelAndView mav = new ModelAndView("composemail");
		mav.getModelMap().put("status", status);
		mav.getModelMap().put("success", success);
		return mav;
	}

	public void setMailSender(EmailSender mailSender) {
		this.mailSender = mailSender;		
	}			
	
}
