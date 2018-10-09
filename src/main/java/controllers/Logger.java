package controllers;

import controllers.dao.LoginAccesDAO;
import views.View;
import java.sql.Connection;
import java.util.Map;

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
            view.print("User with that email dose not exist");
        }
        else if (userController != null) {
            userController.welcomeUser();
            userController.startUserSession();
        }
    }

    private UserController logUser(){
        String email = view.getInputString("Enter email: ");
        String password = view.getInputString("Enter Password");
        Map loginData = new LoginAccesDAO(connection).getLoginData;
        int accesLevel = loginData.get(email).get(1);
        if(loginData.get(email).get(0).equals(password) && accesLevel > 0) {
            userInDatabase = true;
            return createUserController(accesLevel, email);
        }
        return null;
    }

    private UserController createUserController(int accesLevel, String email){
        if (accesLevel == 1){
            return new CodecoolerController(email);
        }
        else if (accesLevel == 2){
            return new MentorController(email);
        }
        else if (accesLevel == 3){
            return new CreepyGuyController(email);
        }
        return null;
    }
}
