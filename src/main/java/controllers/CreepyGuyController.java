package controllers;

import views.View;

import java.sql.Connection;

public class CreepyGuyController extends UserController {
    String email;
    View view;
    Connection connection;

    public CreepyGuyController(String email, Connection connection){
        this.email = email;
        view = new View();
        this.connection = connection;
    }

    @Override
    public void startUserSession() {

        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Log out",
                    "Mentor manager",
                    "Class manager",
                    "Experience level manager");

            option = view.getInputInt(0, 3);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    mentorManager();
                    break;
                case 2:
                    classManager();
                    break;
                case 3:
                    experienceLevelManager();
                    break;
            }
        }

    }

    private void mentorManager(){
        boolean isRunning = true;
        while (isRunning) {

            view.printMenu("Log out",
                    "Mentor manager",
                    "Class manager",
                    "Experience level manager");

            option = view.getInputInt(0, 3);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    mentorManager();
                    break;
                case 2:
                    classManager();
                    break;
                case 3:
                    experienceLevelManager();
                    break;
            }
        }

    }
}
