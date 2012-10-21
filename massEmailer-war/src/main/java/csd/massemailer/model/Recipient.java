package csd.massemailer.model;

import org.apache.commons.lang3.StringUtils;
import csd.massemailer.utils.CommonMsg;
import csd.massemailer.utils.Validation;

public class Recipient{
	private String firstName;
	private String lastName;
	private String email;
	private String errorMessage="";
	private Validation validator = new Validation();

	public Recipient(String firstName, String lastName, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}
	

	public static String validateName(String firstName, String lastName) {
		if(firstName.isEmpty() && lastName.isEmpty())
			return CommonMsg.NAME_MISSING_BOTH_FIRST_LAST_NAME;
		return CommonMsg.NAME_SUCCESS;
	}

	public boolean isValid() {
		if (!validator.validateEmailFormat(email)) {
			errorMessage = CommonMsg.EMAIL_FAIL;
			return false;
		}
		if (StringUtils.isBlank(firstName) && StringUtils.isBlank(lastName)){
			errorMessage = CommonMsg.NAME_MISSING_BOTH_FIRST_LAST_NAME;
			return false;
		}
		return true;
	}
	
	public String getErrorMessage(){
		return errorMessage;
	}
	
	public String toString(){
		return this.email+","+this.firstName+","+this.lastName;
	}
	
}
