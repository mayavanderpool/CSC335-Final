package controller;

import model.AccountManager;
import model.Student;
import model.Teacher;
import view.LoginView;

public class LoginController {
    private AccountManager accountManager;
    private boolean isStudent;
    private LoginView loginView;
    
    public LoginController(AccountManager accountManager, boolean isStudent) {
        this.accountManager = accountManager;
        this.isStudent = isStudent;
        this.loginView = new LoginView(this, isStudent);
    }
    
    public void start() {
        loginView.display();
    }
    
	public void createAccount(String firstName, String lastName, String username, String password) {

		if (accountManager == null) {
			System.out.println("Creating new AccountManager because it was null!");
			accountManager = new AccountManager("people.txt");
		}
		
		if (isStudent) {
			if (accountManager.accountExists(firstName, lastName)) {
				loginView.showError("Account already exists for this person.");
				return;
			}
			if (!accountManager.personExists(firstName, lastName)) {
				accountManager.addStudent(firstName, lastName, username);
			}
		} else {
			if (accountManager.accountExists(firstName, lastName)) {
				loginView.showError("Account already exists for this person.");
				return;
			}
			if (!accountManager.personExists(firstName, lastName)) {
				Teacher newTeacher = new Teacher(firstName, lastName, username);
				accountManager.addTeacher(newTeacher);
			}
			
			
		}
	
		accountManager.addPassword(firstName, lastName, username, password);
		// loginView.close(); ←❌ remove this line
		showLoginForm();      // ←✅ keep this
	}
	
    
    public void login(String username, String password) {
		if (accountManager == null) {
			System.out.println("Creating new AccountManager because it was null in login!");
			accountManager = new AccountManager("people.txt");
		}
        if (accountManager.checkCredentials(username, password)) {
            if (isStudent) {
                Student student = accountManager.getStudentByUsername(username);
                if (student != null) {
                    loginView.close();
                    StudentController studentController = new StudentController(student, accountManager);
                    studentController.start();
                }
            } else {
                Teacher teacher = accountManager.getTeacherByUsername(username);
                if (teacher != null) {
                    loginView.close();
                    TeacherController teacherController = new TeacherController(teacher, accountManager);
                    teacherController.start();
                }
            }
        } else {
            loginView.showError("Invalid username or password.");
        }
    }
    
    public void showLoginForm() {
        loginView.showLoginForm();
    }
    
    public void showSignupForm() {
        loginView.showSignupForm();
    }
    
    public void backToRoleSelection() {
        loginView.close();
        MainController mainController = new MainController(accountManager);
        mainController.start();
    }
}
