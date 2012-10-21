package csd.massemailer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class AwesomeSmtpServer implements SmtpServer {
    private static final Logger LOG = LoggerFactory.getLogger(AwesomeSmtpServer.class);

    private Session session;

    AwesomeSmtpServer() {
        Properties properties = new Properties();
        properties.put("login", "stanlyodde@gmail.com");
        properties.put("password", "what111ever");

        boolean useWiser = System.getProperties().containsKey("USE_WISER");
        properties.put("mail.smtp.host", (useWiser ? "localhost" : "smtp.gmail.com"));
        properties.put("mail.smtp.port", (useWiser ? "2525" : "587"));
        properties.put("mail.smtp.auth", (useWiser ? "false" : "true"));
        properties.put("mail.smtp.starttls.enable", (useWiser ? "false" : "true"));
        LOG.info("using mail properties: {}", properties);

        if (useWiser)
            session = createTestSession(properties);
        else
            session = createSession(properties);
    }

    private Session createSession(Properties properties) {
        String login = (String) properties.get("login");
        String password = (String) properties.get("password");
        return Session.getInstance(properties, createAuthenticator(login, password));
    }

    protected Authenticator createAuthenticator(final String login, final String password) {
        return new StanlyPasswordAuthenticator(login, password);
    }

    protected Session createTestSession(Properties properties) {
        return Session.getInstance(properties);
    }


    public Properties getProperties() {
        return session.getProperties();
    }

    public void send(String recipient, String subject, String body) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setSubject(subject);
        mimeMessage.setText(body);
        mimeMessage.setFrom(new InternetAddress("stanlyodde@gmail.com"));
        mimeMessage.addRecipients(Message.RecipientType.TO, recipient);

        sendInternal(mimeMessage);
    }

    protected void sendInternal(MimeMessage mimeMessage) throws MessagingException {
        Transport.send(mimeMessage);
    }

    public Session getSession() {
        return session;
    }
}

class StanlyPasswordAuthenticator extends Authenticator {
    private String login;
    private String password;

    String getLogin() {
        return login;
    }

    String getPassword() {
        return password;
    }

    StanlyPasswordAuthenticator(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }
}