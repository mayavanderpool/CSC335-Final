
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

	/*INSTANCE VARIABLES*/
	private ArrayList<Person> people;
	private final ArrayList<Observer> observers = new ArrayList<>();

	/*OBSERVER METHODS */
	public void addObserver(Observer o) {
	    observers.add(o);
	}

	public void removeObserver(Observer o) {
	    observers.remove(o);
	}

	private void notifyObservers() {
	    for (Observer o : observers) {
	        o.update();
	    }
	}


	/*CONSTRUCTOR */
	public AccountManager(String filename) {
		people = new ArrayList<Person>();
		inputPeople(filename);
		writeInFile("userinfo.txt", "", true);
	}

	/*
	 * inputPeople(String file) - reads in teachers/students from input file
	 * Return: none
	 */
	public void inputPeople(String file) {

		try {
			Scanner scanner = new Scanner(new File(file));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(",");

				if (split.length == 4) {
					String role = split[0].trim();
					String first = split[1].trim();
					String last = split[2].trim();
					String user = split[3].trim();

					if (role.equalsIgnoreCase("student")) {
						people.add(new Student(first, last, user));
					} else {
						people.add(new Teacher(first, last, user));
					}
				} 

			}

			scanner.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
	}

	/*
	 * addTeacher(Teacher) - adds teacher to account manager
	 * Return: none
	 */
	public void addTeacher(Teacher teacher){
		people.add(teacher);
		notifyObservers();
	}

	/*
	 * addStudent(String, String, String) - adds student to account manager
	 * Return: none
	 */
	public void addStudent(String firstName, String lastName, String username){
		if (!personExists(firstName, lastName)) {
			people.add(new Student(firstName, lastName, username));
			registerStudent(firstName, lastName, username);
		}
		notifyObservers();
	}

	/*
	 * importStudents(String, Course) - import students from text file, adds to course
	 * Return: none
	 */
	public void importStudents(String file, Course course) {
	
		try (Scanner scanner = new Scanner(new File(file))) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine().trim();
				if (line.isEmpty()) continue;
	
				String[] split = line.split(",");
				if (split.length == 3) {
					String last = split[0].trim();
					String first = split[1].trim();
	
					Student student = getStudentByName(first, last);
	
					if (student != null && !course.getStudents().getStudents().contains(student)) {
						course.addStudents(student);
					} 
				} 
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		notifyObservers();
	}
	

	/*
	 * getUsers() - get users that have created a username/have data.
	 * Return: ArrayList of People with accounts
	 */
	public ArrayList<Person> getUsers() {
		ArrayList<Person> users = new ArrayList<Person>();
		for (Person p : people) {
			if (!p.getUserName().equals("")){
				users.add(p);
			}
		}
		return users;
	}

	/*START UP*/

	/*
	 * writeInFile(String, String, boolean) - writes in a file
	 * Return: none
	 */
	private void writeInFile(String filename, String content, boolean dontOverwrite) {
		try (FileWriter writer = new FileWriter(filename, dontOverwrite)) {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*CREATING ACCOUNT*/

	/*
	 * personExists(String, String) - check that user's name is in system
	 * Return: boolean representing if user name already used
	 */
	public boolean personExists(String fname, String lname) {
		for (Person p : people) {
			if (p.getFirstName().equalsIgnoreCase(fname) && p.getLastName().equalsIgnoreCase(lname))
				return true;
		}
		return false;
	}

	/*
	 * accountExists(String, String)check that user doesn't already have
	 *  an account (based on their first and last name)
	 *  Return: boolean representing if account already exists
	 */
	public boolean accountExists(String fname, String lname) {
		for (Person p : people) {
			if (p.getFirstName().equalsIgnoreCase(fname) && p.getLastName().equalsIgnoreCase(lname)
					&& !p.getUserName().isEmpty()) {
				return true;
			} 
		}
		return false;
	}
	

	/*
	 * addPassword(String, String, String, String) - a user is creating a password for their account
	 * Return: none
	 */
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
		notifyObservers();
	}

	/*
	 * registerStudent(String, String, String) - add new student to file
	 * Return: none
	 */
	public void registerStudent(String firstName, String lastName, String user){
		if(!personExists(firstName, lastName)){
			try (FileWriter writer = new FileWriter("people.txt", true)) {  
				writer.write("student" + "," + firstName + "," + lastName + "," + user +"\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}

	/*
	 * getSalt() - salts password
	 * Return : String of Salted password
	 */
	private String getSalt() {
		// salt password
		byte[] salt = new byte[16];
		new SecureRandom().nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	/*
	 * hashPassword(Sting password) - use hash to secure password
	 * Return: String of hashed password
	 */
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

	/*
	 * checkCredentials(String, String) - checks user's credentials when they log in
	 * Return: boolean of if credentials are correct
	 */
	public boolean checkCredentials(String username, String password) {
		if (!usernameExists(username))
			return false;
		return checkPassword(username, password);
	}

	/*
	 * usernameExisits(String) - confirms there is a username
	 * Return: boolean representing in username in database
	 */
	private boolean usernameExists(String username) {
		for (Person p : getUsers()) {
			if (p.getUserName().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * checkPassword(String, String) - compare inputted password with password in text file
	 * Return: boolean based on if password is correct
	 */
	private boolean checkPassword(String username, String password) {
		try (BufferedReader reader = new BufferedReader(new FileReader("userinfo.txt"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(","); // composed of username, salt, and password
				if (parts[0].equals(username)) {
					String inputtedPassword = hashPassword(password + parts[1]);
					if (inputtedPassword.equals(parts[2]))
						return true;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * getStudentsByUsername(String) - gets student from username
	 * Return: Student that matches username
	 */
	public Student getStudentByUsername(String username) {
		for (Person p : getUsers()) {
			if (p instanceof Student && p.getUserName().equals(username)) {
				return (Student) p;
			}
		}
		return null;
	}

	/*
	 * getStudentByName(String, String) - gets student from first and last name
	 * Return: Student that matches names
	 */
	public Student getStudentByName(String fName, String lName) {
		for (Person p : people) {
			if (p instanceof Student && p.getFirstName().equalsIgnoreCase(fName) && p.getLastName().equalsIgnoreCase(lName)) {
				return (Student) p;
			}
		}
		return null;
	}

	/*
	 * getTeacherByUsername(String) - gets teacher from username
	 * Return" Teacher that matches username
	 */
	public Teacher getTeacherByUsername(String username) {
		for (Person p : getUsers()) {
			if (p instanceof Teacher && p.getUserName().equals(username)) {
				return (Teacher) p;
			}
		}
		return null;
	}

}
