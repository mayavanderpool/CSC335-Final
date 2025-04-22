import controller.MainController;
import model.AccountManager;

public class Main {
    public static void main(String[] args) {
        AccountManager accountManager = new AccountManager();
        MainController mainController = new MainController(accountManager);
        mainController.start();
    }
}
