package csd.massemailer.service;


import java.io.IOException;
import java.util.List;

import csd.massemailer.model.Recipient;

public interface StorageProvider {
	boolean addRecipient(Recipient tobeAdded);
	
	List<Recipient> getRecipientList() throws IOException;
}
