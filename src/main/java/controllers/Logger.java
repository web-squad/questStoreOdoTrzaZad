package controllers;

import controllers.dao.LoginAccesDAO;
import views.View;
import java.sql.Connection;

public class Logger {
    private View view;
    private String dbPass;
    private String dbUser;
    private Connection connection;
    private boolean userInDatabase;

    public Logger() {
        view = new View();
        dbPass = view.getInputString("DB Pass?");
        dbUser = view.getInputString("DB User?");
        connection = new Connector().connect(dbUser, dbPass);
        userInDatabase = false;
    }

    public void logIn(){
        UserController userController = logUser();
        if (!userInDatabase && userController == null) {
            view.print("User with that email does not exist");
        }
        else if (userController != null) {
            userController.startUserSession();
        }
    }

    private UserController logUser(){
        String email = view.getInputString("Enter email: ");
        String password = view.getInputString("Enter Password");
        int acessLevel;
        acessLevel = new LoginAccesDAO(connection).readLoginData(email, password);
        return createUserController(acessLevel, email);
    }

    private UserController createUserController(int acessLevel, String email){
        if (acessLevel == 1){
            return new CodecoolerController();
        }
        else if (acessLevel == 2){
            return new MentorController();
        }
        else if (acessLevel == 3){
            return new CreepyGuyController(email, creepyGuyDAO);
        }
        return null;
    }
}
