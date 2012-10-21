package csd.massemailer.utils;

import java.util.List;
import java.util.regex.Pattern;

import csd.massemailer.model.Recipient;

public class Validation {
	
	private static Pattern emailDomainPattern = Pattern.compile("[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

	public boolean validateEmailFormat(String email) {
	   int indexOfAt = email.indexOf('@');
	   if (indexOfAt == -1)
		   return false;
	   if(indexOfAt == 0) 
		   return false;
	   
	   if(!emailDomainPattern.matcher(email.substring(indexOfAt+1)).matches())
		  return false;
	   return true;
	}

	public Boolean isRecipientDuplicate(List<Recipient> recipientList, Recipient recipient) {
		for(Recipient r : recipientList) {
			if (r.getEmail().equals(recipient.getEmail()))
				return true;
		}
		return false;
	}


}
