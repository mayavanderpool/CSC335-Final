package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.AccountManager;
import model.Course;
import model.Student;
import model.Teacher;

class AccountManagerTest {
private static AccountManager aManager;
	
	@BeforeEach
	void setup() {
		aManager = new AccountManager();
		aManager.inputPeople("testAccountManager.txt");
	}
	
	@Test
	void testAddTeacher() {
	    Teacher newTeacher = new Teacher("Alice", "Johnson", "ajohnson");
	    aManager.addTeacher(newTeacher);

	    assertTrue(aManager.personExists("Alice", "Johnson"));
	    assertTrue(aManager.accountExists("Alice", "Johnson"));
	}
	
	@Test
	void testAddStudent() {
	    aManager.addStudent("Charlie", "Brown", "cbrown");
	    assertTrue(aManager.personExists("Charlie", "Brown"));
	    assertTrue(aManager.accountExists("Charlie", "Brown"));
	}
	
	@Test
	void testImportStudents() {
	    String importFileName = "testImportStudents.txt";
	    try (FileWriter writer = new FileWriter(importFileName)) {
	        writer.write("Doe,John,jdoe\n");
	        writer.write("Smith,Jane,jsmith\n");
	    } catch (IOException e) {
	        fail("Failed to create test input file: " + e.getMessage());
	    }
	    aManager.addStudent("John", "Doe", "jdoe");
	    aManager.addStudent("Jane", "Smith", "jsmith");
	    Course course = new Course("Test Course");
	    
	    aManager.importStudents(importFileName, course);
	    assertFalse(course.getStudents().getStudents().isEmpty());
	}

	@Test
	void testGetStudentByUsername() {
	    aManager.addStudent("Lucy", "Goosy", "lgoosy");
	    Student student = aManager.getStudentByUsername("lgoosy");
	    assertNotNull(student, "Student should be found");
	    assertEquals("Lucy", student.getFirstName());
	    assertEquals("Goosy", student.getLastName());
	}

	@Test
	void testGetTeacherByUsername() {
	    Teacher teacher = new Teacher("fname", "lname", "uname");
	    aManager.addTeacher(teacher);
	    Teacher foundTeacher = aManager.getTeacherByUsername("uname");

	    // Validate the retrieved teacher
	    assertNotNull(foundTeacher, "Teacher should be found");
	    assertEquals("fname", foundTeacher.getFirstName());
	    assertEquals("lname", foundTeacher.getLastName());
	}


	@Test
	void testPersonExists() {
	    assertTrue(aManager.personExists("Rees", "Hart"));
	    assertFalse(aManager.personExists("Snuffy", "Snuffleupagus"));
	}
	
	@Test
	void testCheckCredentials() {
	    aManager.addPassword("John", "Smith", "jsmith", "password123");
	    System.out.println();
	    assertTrue(aManager.checkCredentials("jsmith", "password123"));
	    assertFalse(aManager.checkCredentials("jsmith", "wrongpassword"));
	    assertFalse(aManager.checkCredentials("wrongusername", "password123"));
	}
}
