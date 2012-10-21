package csd.massemailer.service;


import csd.massemailer.model.MassEmailerMessage;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailSender implements EmailSender{

    private final SmtpServer smtpServer;

    public JavaEmailSender() {
        smtpServer = new AwesomeSmtpServer();
    }

    public JavaEmailSender(SmtpServer smtpServer) {
        this.smtpServer = smtpServer;
    }

	public void sendMail(MassEmailerMessage message) throws Exception{
		for(String recipientEmail : message.getRecipients() ) {
            smtpServer.send(recipientEmail, message.getSubject(), message.getBody());
		}
	}

}
