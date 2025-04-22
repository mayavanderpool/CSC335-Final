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
			// Define original content - this should match exactly what your original
			// people.txt contains
			String originalContent = "student,Rees,Hart,rhart\n" +
					"student,Crispin,Carter,ccarter\n" +
					"student,Grace,Lizama,glizama\n" +
					"student,Alyssa,Kushlan,akushlan\n" +
					"student,Ava,Tucker,atucker\n" +
					"student,Maddie,Crossen,mcrossen\n" +
					"student,Caroline,Digiovani,cdigiovani\n" +
					"student,Sam,Smith,ssmith\n" +
					"student,Jane,Doe,jdoe\n" +
					"student,Audrey,Smith,asmith\n" +
					"student,Cate,Hart,chart\n" +
					"student,Sarah,Gibson,sgibson\n" +
					"student,Joe,Anderson,janderson\n" +
					"student,Abby,Davis,adavis\n" +
					"student,Ilana,Glazer,iglazer\n" +
					"student,Abbi,Jacobson,ajacobson\n" +
					"student,Zoe,Brown,zbrown\n" +
					"student,Jesse,Jones,jjones\n" +
					"student,Bob,Dylan,bdylan\n" +
					"student,Walter,White,wwhite\n";

			// Write the original content back to people.txt
			try (FileWriter writer = new FileWriter("people.txt", false)) { // false = overwrite
				writer.write(originalContent);
				System.out.println("people.txt has been reset to its original state");
			}
		} catch (IOException e) {
			System.out.println("Error resetting people.txt file");
			e.printStackTrace();
		}
	}
}
