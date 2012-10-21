package csd.massemailer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import csd.massemailer.model.MassEmailerMessage;
import csd.massemailer.service.EmailSender;
import csd.massemailer.utils.CommonMsg;

public class EmailControllerTest {
	private MassEmailerMessage messageToBeReceived;
	private EmailController mailController = new EmailController();
	private EmailSender mockSender = new EmailSender(){
		public void sendMail(MassEmailerMessage message) throws Exception {
			messageToBeReceived = message;
		}
	};

	@Before
	public void setUpMockSender() {
		mailController.setMailSender(mockSender);
	}
	
	@Test
	public void testIndexPage() {
		assertEquals("composemail", mailController.getMeIndex().getViewName());
	}

	@Test
	public void testSendEmailSuccessful() throws Exception {
		ModelAndView view = mailController.postComposeForm("maysam@gmail.com", "subject", "body info");
		assertEquals("composemail", view.getViewName());
		assertEquals(CommonMsg.EMAIL_SUCCESS_SEND, view.getModelMap().get("status"));
		assertEquals("body info", this.messageToBeReceived.getBody());
		assertEquals("subject", this.messageToBeReceived.getSubject());
		assertTrue(this.messageToBeReceived.getRecipients().contains("maysam@gmail.com"));
	}

	@Test
	public void testSendMultipleEmailSuccessful() throws Exception {
		mailController.postComposeForm("maysam@gmail.com,steve.jobs@apple.com", "subject", "body info");
		MassEmailerMessage mockReceive = this.messageToBeReceived;
		assertEquals("body info", mockReceive.getBody());
		assertEquals("subject", mockReceive.getSubject());
		ArrayList<String> toPpl = new ArrayList<String>();
		toPpl.add("maysam@gmail.com");
		toPpl.add("steve.jobs@apple.com");
		assertTrue(toPpl.containsAll(mockReceive.getRecipients()));
		assertEquals(2, this.messageToBeReceived.getRecipients().size());
	}

	@Test
	public void testEmailNotSuccessfullySent() throws Exception {
		mockSender = new EmailSender(){
			public void sendMail(MassEmailerMessage message) throws Exception {
				throw new Exception("error");
			}
		};
		mailController.setMailSender(mockSender);
		ModelAndView view = mailController.postComposeForm("maysam@gmail.com", "subject", "body info");
		assertEquals("composemail", view.getViewName());
		assertEquals("error", view.getModelMap().get("status"));
	}
}
