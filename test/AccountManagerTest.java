package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
		aManager = new AccountManager("testAccountManager.txt");
	}
	
	@Test
	void testImportPeople() {
		String testFile = "testFile.txt";
	    try (FileWriter writer = new FileWriter(testFile)) {
	        writer.write("student, Jay, Say, jsmith\n");
	        writer.write("teacher, Ana, Banana, asmith\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    aManager.inputPeople(testFile);
	    assertTrue(aManager.personExists("Jay", "Say"));
	    assertTrue(aManager.personExists("Ana", "Banana"));

	    // Clean up the test file after the test
	    File file = new File(testFile);
	    file.delete();
	}
	
	@Test
	void testAddTeacher() {
	    Teacher newTeacher = new Teacher("Alice", "Johnson", "ajohnson");
	    aManager.addTeacher(newTeacher);

	    assertTrue(aManager.personExists("Alice", "Johnson"));
	    assertTrue(aManager.accountExists("Alice", "Johnson"));
	    assertFalse(aManager.accountExists("random", "person"));
	}
	
	@Test
	void testAddStudent() {
	    aManager.addStudent("Charlie", "Brown", "cbrown");
	    assertTrue(aManager.personExists("Charlie", "Brown"));
	    assertTrue(aManager.accountExists("Charlie", "Brown"));
	}
	
	@Test
	void testImportStudents() {
	    String fname = "testImportStudents.txt";
	    try (FileWriter writer = new FileWriter(fname)) {
	        writer.write("Doe,John,jdoe\n");
	        writer.write("Smith,Jane,jsmith\n");
	    } catch (IOException e) {
	        fail("Failed to create test file: " + e.getMessage());
	    }
	    aManager.addStudent("John", "Doe", "jdoe");
	    aManager.addStudent("Jane", "Smith", "jsmith");
	    Course course = new Course("Test Course");
	    
	    aManager.importStudents(fname, course);
	    assertFalse(course.getStudents().getStudents().isEmpty());
	    File file = new File(fname);
	    file.delete();
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

	    assertNotNull(foundTeacher, "Teacher should be found");
	    assertEquals("fname", foundTeacher.getFirstName());
	    assertEquals("lname", foundTeacher.getLastName());
	}


	@Test
	void testPersonExists() {
		aManager.addStudent("John", "Smith", "jsmith");
	    assertTrue(aManager.personExists("John", "Smith"));
	    assertFalse(aManager.personExists("Snuffy", "Snuffleupagus"));
	}
	
	@Test
	void testCheckCredentials() {
	    aManager.addStudent("John", "Smith", "jsmith");
	    assertTrue(aManager.checkCredentials("jsmith", "password123"));
	    assertFalse(aManager.checkCredentials("jsmith", "wrongpassword"));
	    assertFalse(aManager.checkCredentials("wrongusername", "password123"));
	}
	
	@Test
	void testAddPassword() {
		aManager.addStudent("John", "Smith", "jsmith");
        aManager.addPassword("John", "Smith", "jsmith", "password123");

        File file = new File("userinfo.txt");
        boolean hasUsername = false;
        boolean hasPassword = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("jsmith")) {
                    hasUsername = true;
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        hasPassword = true;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(hasUsername);
        assertTrue(hasPassword);
    }
	
	@Test
	void testNulls() {
		AccountManager aman = new AccountManager("testEmptyFile.txt");
		assertNull(aman.getStudentByUsername("uname"));
		assertNull(aman.getStudentByName("fname", "lname"));
		assertNull(aman.getTeacherByUsername("uname"));
	}
}
