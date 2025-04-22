package controller;

import model.AccountManager;
import view.MainView;

public class MainController {
    private AccountManager accountManager;
    private MainView mainView;
    
    public MainController(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.mainView = new MainView(this);
    }
    
    public void start() {
        mainView.display();
    }
    
    public void loginAsStudent() {
        LoginController loginController = new LoginController(accountManager, true);
        loginController.start();
        mainView.close();
    }
    
    public void loginAsTeacher() {
        LoginController loginController = new LoginController(accountManager, false);
        loginController.start();
        mainView.close();
    }
}
