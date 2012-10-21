package csd.massemailer.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import csd.massemailer.utils.Validation;

public class MassEmailerMessage {
	private List<String> recipients = new LinkedList<String>();
	private String subject;
	private String body;
	private String errorMsg="";
	private Validation validator = new Validation();

	public MassEmailerMessage(List<String> recipients, String subject, String content) {
		this.subject = subject;		
		this.body = content;
		for (String recipient : recipients) {
			this.recipients.add(recipient);
		}	
	}
	
	public MassEmailerMessage(String recipients, String subject, String content) {
		this.subject = subject;		
		this.body = content;
		for (String recipient : Arrays.asList(recipients.split(","))) {
			this.recipients.add(recipient);
		}
	}

	public void setValidator(Validation validator) {
		this.validator = validator;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	

    public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
    	
    }
	
	public boolean isValid(){
		
		if(recipients.size()<1){
			errorMsg = "Email is required";
			return false;
		}
		
		for(String recipient: recipients){
			if(!validator.validateEmailFormat(recipient) ){
				errorMsg += "Email format error for "+ recipient;
				return false;
			}
		}

		if(this.subject.isEmpty()) {
			errorMsg += "The subject is missing.";
			return false;
		}
		
		if(this.body.isEmpty()){
			errorMsg += "The email body is missing";
			return false;
		}
		errorMsg = "no error";
		return true;
	}
	
	public String getErrorMessage(){
		return errorMsg;
	}

	public String getSubject() {
		return subject;
	}

	public String getBody() {
		return body;
	}
}
