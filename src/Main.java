import java.io.FileWriter;
import java.io.IOException;

import controller.MainController;
import model.AccountManager;

public class Main {
    public static void main(String[] args) {
		
        AccountManager accountManager = new AccountManager();
        MainController mainController = new MainController(accountManager);
        mainController.start();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			resetPeopleFile();
		}));
    }

	private static void resetPeopleFile() {
    try {
        // Define original content - this should match exactly what your original people.txt contains
        String originalContent = 
            "student,Rees,Hart,rhart\n" +
            "student,Crispin,Carter,ccarter\n" +
            "student,Grace,Lizama,glizama\n" +
            "student,Alyssa,Kushlan,akushlan\n" +
            // Add all your original entries here
            "student,Walter,White,wwhite\n";
            
        // Write the original content back to people.txt
        try (FileWriter writer = new FileWriter("people.txt", false)) {  // false = overwrite
            writer.write(originalContent);
            System.out.println("people.txt has been reset to its original state");
        }
    } catch (IOException e) {
        System.out.println("Error resetting people.txt file");
        e.printStackTrace();
    }
}
}
