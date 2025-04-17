
package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class AccountManager {
	
	private ArrayList<Person> people;
	
	public AccountManager() {
		people = new ArrayList<Person>();
		inputPeople("people.txt");
		writeInFile("userinfo.txt", "", true);
		// loadInUsers(); not done with yet!!
	}
	
	
	
	
	// reads in user data and instantiates courses, assignments, grades for students and teachers
	private void loadInUsers() {
		if (people.size() == 0) {  // first time running the program
			inputPeople("people.txt");
			return;
		}
		
		// read in all user data
		File userInfo = new File("data.txt");
		try (Scanner scn = new Scanner(userInfo)) {
			// read in file here. use scanner as parameter for methods
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		};
	}
	
	
	
	
	// read out all user data
	private void loadOutUsers() {
		// read out teachers
		String content = "";
		content += readOutTeachers();
		content += readOutStudents();
		writeInFile("data.txt", content, false);  // all data stored in this file
	}
	
	private String readOutTeachers() {
		String content = "";
		for (Person p : people) {
			if (p instanceof Teacher) {
				content += p.toString();
			}
		}
		return content;
	}
	
	private String readOutStudents() {
		String content = "";
		for (Person p : people) {
			if (p instanceof Student) {
				content += p.toString();
			}
		}
		return content;	
	}
	
	
	
	
	
	
	
	
	
	// reads in teachers/students from input file
	private void inputPeople(String file){

		try {
			Scanner scanner = new Scanner(new File(file));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(",");
				
				if (split.length == 3) {
					String last = split[0].trim();
					String first = split[1].trim();
					String stuOrTeach = split[2].trim();
					if ("student".equalsIgnoreCase(stuOrTeach)) {
						Person p = new Student(first, last);
						people.add(p);
					} else {
						Person p = new Teacher(first, last);
						people.add(p);
					}
				} else System.out.println("Invalid line");
			}
			
			scanner.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
	}
	
	
	// get users that have created a username/have data.
	public ArrayList<Person> getUsers() {
		ArrayList<Person> users = new ArrayList<Person>();
		for (Person p : people) {
			if (!p.getUserName().equals("")) users.add(p);
		}
		return users;
	}
	
	
	// start up
	
	
	// writes in a file
	private void writeInFile(String filename, String content, boolean dontOverwrite) {
		try (FileWriter writer = new FileWriter(filename, dontOverwrite)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	// creating an account
	
	// check that user's name is in system
	public boolean personExists(String fname, String lname) {
		for (Person p : people) {
			if (p.getFirstName().equalsIgnoreCase(fname) && p.getLastName().equalsIgnoreCase(lname)) return true;
		}
		return false;
	}
	
	
	// check that user doesn't already have an account (based on their first and last name)
	public boolean accountExists(String fname, String lname) {
		for (Person p : people) {
			if (p.getFirstName().equalsIgnoreCase(fname) && p.getLastName().equalsIgnoreCase(lname)) {
				if (!p.getUserName().equals("")) return true;
				else return false;
			}
		}
		return false;  // should never reach this point
	}
	
	// make sure username doesn't exist yet. if it does, add to end of username
	public String createUsername(String fname, String lname) {
		return (fname.substring(0,1) + lname).toLowerCase();
	}

	public void addTeacher(Teacher teacher){
		people.add(teacher);
	}
	
	// a user is creating a password for their account
	public void addPassword(String fname, String lname, String username, String password) {
		String salt = getSalt();
		String saltedHashed = hashPassword(password + salt);
		// add username to person
		for (Person p : people) {
			if (p.getFirstName().equalsIgnoreCase(fname) && p.getLastName().equalsIgnoreCase(lname)) {
				p.setUser(username);
			}
		}
		writeInFile("userinfo.txt", username + "," + salt + "," + saltedHashed + "\n", true);
	}
	
	private String getSalt() {
		// salt password
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
	
	private String hashPassword(String password) {
		MessageDigest md;
		String hashedPassword = "";
		try {
			md = MessageDigest.getInstance("SHA-256");
			byte[] hashing = md.digest(password.getBytes());
			hashedPassword = Base64.getEncoder().encodeToString(hashing);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hashedPassword;
	}
	
	
	// checks user's credentials when they log in
	
	public boolean checkCredentials(String username, String password) {
		if (!usernameExists(username)) return false;
		return checkPassword(username, password);
	}
	
	// confirms there is a username
	private boolean usernameExists(String username) {
		for (Person p : getUsers()) {
			if (p.getUserName().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}
	
	// compare inputted password with password in text file
	private boolean checkPassword(String username, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader("userinfo.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");  // composed of username, salt, and password
				if (parts[0].equals(username)) {
					String inputtedPassword = hashPassword(password + parts[1]);
					if (inputtedPassword.equals(parts[2])) return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}