package csd.massemailer.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.StringTokenizer;

import csd.massemailer.model.Recipient;

public class FileStorageProviderImpl implements StorageProvider {
	String fileAddress = "";
	File recipientFile;
	private PrintWriter pWriter;
	List<Recipient> memDb = new ArrayList<Recipient>();
	int size = 0;
	
	public FileStorageProviderImpl() {
	}
	
	public boolean addRecipient(Recipient tobeAdded) {
		try {
			appendRecipientToFile(convertToFileFormat(tobeAdded));
			memDb.add(tobeAdded);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public List<Recipient> getRecipientList() throws IOException {
		if (memDb.size() != size)
			reloadDb();
		return memDb;
	}

	private void reloadDb() throws IOException {
		BufferedReader br = null;
		StringTokenizer st;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileAddress));
			while ((sCurrentLine = br.readLine()) != null) {
				st = new StringTokenizer(sCurrentLine, ",");
				memDb.add(new Recipient(st.nextToken(), st.nextToken(), st.nextToken()));
			}

		} finally {
			if (br != null)
				br.close();
		}

	}

	private String convertToFileFormat(Recipient r) {
		return r.getEmail() + "," + r.getFirstName() + "," + r.getLastName();
	}

	private void createRecipientFile() throws IOException {
		recipientFile.createNewFile();
	}

	private void appendRecipientToFile(String entry) throws IOException {
			this.pWriter.println(entry);
			size++;
	}

	public void setWriter(PrintWriter pWriter) {
		this.pWriter = pWriter;

	}

}
