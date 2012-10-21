package csd.massemailer;
import static org.junit.Assert.assertEquals;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class LearningJavaEmailIntegrationTest {
	Wiser wiser = new Wiser();
	int port = 40000 + new Random().nextInt(10000);

	@Before
	public void setUpWiser() {
		wiser.setPort(port);
		wiser.start();
	}

	@Test
	public void sendARealEmailThroughJavaMailAndReceivedEmailShouldBeTheSame() throws Exception {
		Properties props = new Properties();
		props.put("mail.smtp.host", "localhost");
		props.put("mail.smtp.port", String.valueOf(port));

		Session session = Session.getInstance(props);

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("stanly.lau@gmail.com"));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("stanly@odd-e.com"));
		message.setSubject("Testing Subject");
		message.setText("BODY CONTENT");

		Transport.send(message);

		assertEquals(1, wiser.getMessages().size());

		WiserMessage wiserMessage = wiser.getMessages().get(0);

		assertEquals("stanly@odd-e.com", wiserMessage.getEnvelopeReceiver());
		assertEquals("stanly.lau@gmail.com", wiserMessage.getEnvelopeSender());
		assertEquals("BODY CONTENT", wiserMessage.getMimeMessage().getContent().toString());
	}
}
