package controllers;

import controllers.dao.CreepyGuyDAO;
import views.View;

import java.sql.Connection;

public class CreepyGuyController extends UserController {
    String email;
    View view;
    CreepyGuyDAO dao;

    public CreepyGuyController(String email, CreepyGuyDAO dao){
        this.email = email;
        view = new View();
        this.dao = dao;
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
        int option;

        while (isRunning) {

            view.printMenu("Quit",
                    "Add Mentor",
                    "Edit Mentor",
                    "Delete Mentor");

            option = view.getInputInt(0, 3);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    addMentor();
                    break;
                case 2:
                    editMentor();
                    break;
                case 3:
                    deleteMentor();
                    break;
            }
        }

    }

    private void classManager(){

        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Quit",
                    "Add Class",
                    "Delete Class",
                    "Edit Class");

            option = view.getInputInt(0, 3);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    addClass();
                    break;
                case 2:
                    editClass();
                    break;
                case 3:
                    deleteClass();
                    break;
            }
        }
    }

    private void experienceLevelManager(){

        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Quit",
                    "Add Mentor",
                    "Edit Mentor",
                    "Delete Mentor");

            option = view.getInputInt(0, 3);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    addLevel();
                    break;
                case 2:
                    editLevel();
                    break;
                case 3:
                    deleteLevel();
                    break;
            }
        }

    }
}
