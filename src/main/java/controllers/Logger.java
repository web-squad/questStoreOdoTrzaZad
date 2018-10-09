package controller;

import view.View;

import java.sql.Connection;

public class Logger {
    private View view;
    private String dbPass;
    private String dbUser;
    private Connection connection;

    public Logger() {
        view = new View();
        dbPass = view.getInputString("DB Pass?");
        dbUser = view.getInputString("DB User?");
        connection = new Connector().connect(dbUser, dbPass);
    }

    public void logIn(){
        userController = null;
        String email = view.getInputString("Enter email: ");
        setControllerIfEmailOnStudentsList(email);
        setControllerIfEmailOnMentorsList(email);
        setControllerIfEmailOnEmployeesList(email);
        setControllerIfEmailOnManagersList(email);

        if (!userInDatabase && userController == null) {
            view.print("User with that email dose not exist");
        }
        else if (userController != null) {
            userController.welcomeUser();
            userController.startUserSession();
        }
    }
}
