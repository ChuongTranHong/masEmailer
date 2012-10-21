package com.wiser;

import java.util.List;

import javax.mail.MessagingException;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

public class WiserLibrary {

	public static final int WISER_PORT = 2525;
	private Wiser wiser;

	public WiserLibrary() {
		this.wiser = new Wiser();
	}

	public WiserLibrary(Wiser wiser) {
		this.wiser = wiser;
	}

	public void startWiser() {
		wiser.setPort(WISER_PORT);
		wiser.start();
	}

	public void stopWiser() {
		wiser.stop();
	}

	public void checkEmail(String recipient, String subject, String body) {
		List<WiserMessage> messages = wiser.getMessages();
		failIfIncorrectNumberOfReceivedMessages(messages);
		WiserMessage firstMessage = messages.get(0);			
		failIfWrongRecipient(recipient, firstMessage);
		failIfWrongSubject(subject, firstMessage);
		failIfWrongBody(body, firstMessage);
	}

	private void failIfWrongBody(String body, WiserMessage message) {
		String actualBody = getActualBody(message);
		if (!actualBody.equals(body)) throwUp("Not expected body, expected: " + body + ", got: " + actualBody);
	}

	private void throwUp(String m) {
		throw new IllegalArgumentException(m);
	}

	private void failIfWrongSubject(String subject, WiserMessage message) {
		String actualSubject = getActualSubject(message);
		if (!actualSubject.equals(subject)) throwUp("Not expected subject, expected: " + subject + ", got: " + actualSubject);
	}

	private void failIfWrongRecipient(String recipient, WiserMessage message) {
		String actualRecipient = message.getEnvelopeReceiver();			
		if (actualRecipient == null || !actualRecipient.equals(recipient)) throwUp("Not expected recipient, expected: " + recipient + ", got: " + actualRecipient);		
	}

	private void failIfIncorrectNumberOfReceivedMessages(List<WiserMessage> messages) {
		if (messages == null || messages.size() != 1)
			throwUp("Received incorrect number of messages, expected: 1, received: " + (messages != null ? messages.size() : "null"));
	}

	private String getActualSubject(WiserMessage msg) {
		try {
			return msg.getMimeMessage().getSubject();
		} catch (MessagingException e) {
			throw new RuntimeException("could not fetch Subject", e);
		}
	}
	
	private String getActualBody(WiserMessage msg) {
		try {
			return msg.getMimeMessage().getContent().toString();
		} catch (Exception e) {
			throw new RuntimeException("could not fetch Subject", e);
		}
	}
	
}
