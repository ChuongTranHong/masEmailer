package csd.massemailer.service;


public interface SmtpServer {

    void send(String recipientEmailAddress, String subject, String body) throws Exception;
}
