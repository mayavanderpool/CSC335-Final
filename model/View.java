package model;
/*
 * File: View.java
 * Author: Maya Vanderpool
 * Purpose: 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class View{
	private Scanner scanner;
	private ArrayList<Student> allStudents;

	public View(){
		this.scanner = new Scanner(System.in);
		this.allStudents = inputStudents();
	}

	public ArrayList<Student> inputStudents(){
		ArrayList<Student> students = new ArrayList<Student>();
		String file = "students.txt"; 

		try {
			Scanner scanner = new Scanner(new File(file));
			
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] split = line.split(",");
				
				if (split.length == 2) {
					String last = split[0].trim();
					String first = split[1].trim();
					 
					Student s = new Student(first, last);
					students.add(s);
					
				} else {
					System.out.println("Invalid line");
				}
			}
			
			scanner.close();
		} catch (FileNotFoundException exception) {
			System.out.println("File does not exist");
			exception.printStackTrace();
		}
		return students;
	}

	
	public static void main(String[] args){
		View view = new View();
		for(Student s : view.allStudents){
			System.out.println(s.getFirstName() + s.getLastName());
		}
	}
 }
