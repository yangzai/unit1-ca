package sg.edu.nus.iss.se24_2ft.unit1.ca;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSV;
import sg.edu.nus.iss.se24_2ft.unit1.ca.util.CSVReader;

/**
 * @author: Tran Ngoc Hieu
 */
public class CustomerManager {
	private HashMap<String, Member> membersMap = new HashMap<String, Member>();
	private String filename;
	private CSV csvIO;

	public CustomerManager(String filename) throws IOException {
		this.filename = filename;
		initdata();
	}

	private void initdata() throws IOException {
		CSVReader reader = null;
		try {
			reader = new CSVReader(filename);
			csvIO = new CSV(filename);
			while (reader.readRecord()) {
				Object[] keepers = reader.getValues().toArray();

				String name = keepers[0].toString();
				String memberID = keepers[1].toString();
				int loyaltyPoint = -1;
				try {
					// Convert the loyaltyPoint from String to int
					loyaltyPoint = Integer.parseInt(keepers[2].toString());
				} catch (NumberFormatException nfe) {
					System.out.println("Member " + memberID + " NumberFormatException: " + nfe.getMessage());
				}
				Member member = new Member(memberID, name, loyaltyPoint);
				membersMap.put(memberID, member);
			}
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		} finally {
			if (reader != null)
				reader.close();
		}

	}

	public void addMember(String memberID, String name) {
		Member m = new Member(memberID, name);
		if (!membersMap.containsKey(memberID)) {
			membersMap.put(memberID, m);
			try {
				update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Member removeMember(String memberID) {
		Member m = membersMap.remove(memberID);
		if (m != null) {
			try {
				update();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return m;
	}
	
	public Customer getCustomer(String memberID){
		if (memberID == null) {
			return PublicCustomer.getInstance();
		} else {
			return getMember(memberID);
		}
	}
	
	public Member getMember(String memberID) {
		return membersMap.get(memberID);
	}

	public ArrayList<Member> getMembersAsList() {
		return new ArrayList<Member>(membersMap.values());
	}

	public void update() throws IOException {
		ArrayList<String> contents = new ArrayList<String>();
	    for (Member member : this.getMembersAsList()) {
			contents.add(member.toString());
		}
	    csvIO.write(contents);
	}

}
