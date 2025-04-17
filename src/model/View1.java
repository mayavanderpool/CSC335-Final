
 package model;
 
 import java.util.Scanner;
 
 import javax.swing.*;
 
  public class View1{
 	 private static AccountManager aManager;
 	 private static Scanner s;
 
 	public static void main(String[] args){
 		s = new Scanner(System.in);
 		aManager = new AccountManager();  // reads in users from file and stores them
 		// createWindow();
 		logInPage();
 		s.close();
 	}
 	
 	public static void logInPage() {
 		// first branch: create account or continuing user
 		System.out.println("new or continuing user? (new/con)");
 		String input = s.nextLine();
 		if (input.equalsIgnoreCase("new")) createAccount();
 		continuingUser(); // continuing user: check username and password. updates gui w their data
 	}
 	
 	private static void createAccount() {
 		Scanner s = new Scanner(System.in);
 		System.out.print("enter first name: ");
 		String fname = s.nextLine();
 		System.out.print("enter last name: ");
 		String lname = s.nextLine();
 		if (!aManager.personExists(fname, lname)) { // if user is not in imported list
 			System.out.println("Person not found.");
 		} else if (aManager.accountExists(fname, lname)) {  // if user already made an account, move to continuing user
 			System.out.println("Account already exists for this person.");
 		} else {  // create new account
 			String username = aManager.createUsername(fname, lname); // generates username and adds to person username variable
 			System.out.println("Your username is " + username);
 
 			// set password. the user makes the password and we store it
 			System.out.print("Create a password: ");
 			String password = s.nextLine();
 			aManager.addPassword(fname, lname, username, password);
 			System.out.println("Account created.");
 			return;
 		}
 	}
 	
 	private static void continuingUser() {
 		System.out.println("username: ");
 		String username = s.nextLine();
 		System.out.print("password: ");
 		String password = s.nextLine();
 		// validate credentials to let user in
 		boolean valid = aManager.checkCredentials(username, password);
 		if (!valid) {
 			System.out.println("Invalid username or password.");
 		} else System.out.println("Valid password. Welcome.");
 	}
  }