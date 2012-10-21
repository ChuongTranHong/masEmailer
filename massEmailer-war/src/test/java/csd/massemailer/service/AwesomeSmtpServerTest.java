package csd.massemailer.service;

import csd.massemailer.service.AwesomeSmtpServer;
import csd.massemailer.service.StanlyPasswordAuthenticator;
import org.junit.After;
import org.junit.Test;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class AwesomeSmtpServerTest {

    @After
    public void cleanupSystemProperties() {
        System.getProperties().remove("USE_WISER");
    }

    @Test
    public void checkMailSessionProperties() {
        AwesomeSmtpServer smtpServer = new AwesomeSmtpServer();
        Properties mailProps = smtpServer.getProperties();
        assertEquals("smtp.gmail.com", mailProps.get("mail.smtp.host"));
        assertEquals("587", mailProps.get("mail.smtp.port"));
        assertEquals("true", mailProps.get("mail.smtp.auth"));
        assertEquals("true", mailProps.get("mail.smtp.starttls.enable"));

        assertEquals("stanlyodde@gmail.com", mailProps.get("login"));
        assertEquals("what111ever", mailProps.get("password"));
    }

    @Test
    public void checkTestMailSessionProperties(){
        System.getProperties().setProperty("USE_WISER", "true");
        AwesomeSmtpServer awesomeSmtpServer = new AwesomeSmtpServer();
        Properties mailProps = awesomeSmtpServer.getProperties();
        assertEquals("localhost", mailProps.get("mail.smtp.host"));
        assertEquals("2525", mailProps.get("mail.smtp.port"));
        assertEquals("false", mailProps.get("mail.smtp.auth"));
        assertEquals("false", mailProps.get("mail.smtp.starttls.enable"));
    }

    @Test
    public void testMailSend() throws Exception {
        final MimeMessage[] messages = new MimeMessage[1];
        AwesomeSmtpServer smtpServer = new AwesomeSmtpServer() {
            protected void sendInternal(MimeMessage msg) {
                messages[0] = msg;
            }

        };
        smtpServer.send("some-recipient", "some-subject", "some-body");

        MimeMessage msg = messages[0];
        String actualRecipient = msg.getRecipients(Message.RecipientType.TO)[0].toString();
        assertEquals("some-recipient", actualRecipient);
        assertEquals("some-subject", msg.getSubject());
        assertEquals("some-body", msg.getContent());
        assertEquals("stanlyodde@gmail.com", msg.getFrom()[0].toString());
    }

    @Test
    public void checkMailSessionAuthenticator(){
        final Authenticator[] auths = new Authenticator[1];
        new AwesomeSmtpServer() {
            @Override
            protected Authenticator createAuthenticator(String login, String password) {
                auths[0] = super.createAuthenticator("some-login", "some-password");
                return auths[0];
            }
        };

        StanlyPasswordAuthenticator stanlyAuthenticator = (StanlyPasswordAuthenticator) auths[0];
        assertEquals("some-login", stanlyAuthenticator.getLogin());
        assertEquals("some-password", stanlyAuthenticator.getPassword());
    }

}
