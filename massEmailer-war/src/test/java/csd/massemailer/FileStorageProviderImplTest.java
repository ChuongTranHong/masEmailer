package csd.massemailer;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import csd.massemailer.model.Recipient;
import csd.massemailer.service.*;

public class FileStorageProviderImplTest {
	
	PrintWriter pWriter=mock(PrintWriter.class);
	BufferedReader bReader = mock(BufferedReader.class); 
	
	@Test
	public void Add1RecipientThenCheckTheStoringFormat(){
		FileStorageProviderImpl fileStorage = new FileStorageProviderImpl() {
		};
		
		fileStorage.setWriter(pWriter);
		Recipient tobeAdded = new Recipient("steve", "jobs","abc@def.com");
		fileStorage.addRecipient(tobeAdded);
		verify(pWriter).println("abc@def.com,steve,jobs");
	}
	
	@Test
	public void FirstTryThenExpectNoRecipientReturned() throws IOException {
		FileStorageProviderImpl fileStorage = new FileStorageProviderImpl();
		assertEquals(fileStorage.getRecipientList().size(),0);
	}
	
	@Test
	public void Add1RecipientsThenExpectReturning1Recipients() throws IOException {
		FileStorageProviderImpl fileStorage = new FileStorageProviderImpl();
		fileStorage.setWriter(pWriter);
		Recipient tobeAdded = new Recipient("steve", "jobs","abc@def.com");
		fileStorage.addRecipient(tobeAdded);
		
		List storedRecipients=fileStorage.getRecipientList();
		assertEquals(storedRecipients.get(0).toString(),tobeAdded.toString());	
	}
	
	@Test
	public void Add2RecipientsThenExpectReturning2Recipients() throws IOException {
		FileStorageProviderImpl fileStorage = new FileStorageProviderImpl();
		fileStorage.setWriter(pWriter);
		Recipient tobeAdded1 = new Recipient("steve", "jobs","abc@def.com");
		fileStorage.addRecipient(tobeAdded1);
		
		Recipient tobeAdded2 = new Recipient("Tim", "Cook","timCook@def.com");
		fileStorage.addRecipient(tobeAdded2);
		
		List storedRecipients=fileStorage.getRecipientList();
		assertEquals(storedRecipients.get(1).toString(),tobeAdded2.toString());	
	}
	
}
