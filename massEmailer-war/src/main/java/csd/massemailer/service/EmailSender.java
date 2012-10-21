package csd.massemailer.service;

import csd.massemailer.model.MassEmailerMessage;

public interface EmailSender {

	void sendMail(MassEmailerMessage message) throws Exception;
}
