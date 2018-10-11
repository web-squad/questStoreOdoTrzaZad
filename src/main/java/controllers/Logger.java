package controllers;

import controllers.dao.CodecoolerDAO;
import controllers.dao.CreepyGuyDAO;
import controllers.dao.LoginAccesDAO;
import controllers.dao.MentorDAO;
import views.View;
import java.sql.Connection;
import java.util.List;

public class Logger {
    private View view;
    private String dbPass;
    private String dbUser;
    private Connection connection;

    public Logger() {
        view = new View();
        dbPass = "quest";
        dbUser = "queststore";
        connection = new Connector().connect(dbUser, dbPass);
    }

    public void logIn(){
        UserController userController = logUser();
        if (userController != null) {
            userController.startUserSession();
        }
    }

    private UserController logUser(){
        String email = view.getInputString("Enter email: ");
        String password = view.getInputString("Enter Password: ");
        List<Integer> loginData = new LoginAccesDAO(connection).readLoginData(email, password);
        int accessLevel = 0;
        int id = 0;
        if (loginData != null) {
            accessLevel = loginData.get(0);
            id = loginData.get(1);
        }
        return createUserController(accessLevel, id);
    }

    private UserController createUserController(int acessLevel, int id){
        if (acessLevel == 1){
            //CodecoolerDAO codecoolerDao = new CodecoolerDAO(connection);
            //return new CodecoolerController(id, codecoolerDao);
        }
        else if (acessLevel == 2){
            MentorDAO mentorDao = new MentorDAO(connection);
            return new MentorController(id, mentorDao);
        }
        else if (acessLevel == 3){
            CreepyGuyDAO creepyGuyDAO = new CreepyGuyDAO(connection);
            return new CreepyGuyController(id, creepyGuyDAO);
        }
        return null;
    }
}
