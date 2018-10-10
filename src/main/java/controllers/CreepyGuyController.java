package controllers;

import controllers.dao.CreepyGuyDAO;
import models.MentorModel;
import views.View;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreepyGuyController extends UserController {
    String email;
    View view;
    CreepyGuyDAO dao;
    List<String> mentorData;
    MentorModel mentor;
    Room room;
    Level level;

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
                    roomManager();
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

    private void roomManager(){

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
                    addRoom();
                    break;
                case 2:
                    editRoom();
                    break;
                case 3:
                    deleteRoom();
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

    private void addMentor(){
        mentor = new MentorModel(collectMentorData());
        if (mentor != null) dao.addMentor(mentor);
    }

    private void editMentor(){
        fetchMentor();
        editMentorData();
        if (mentor != null) {
            dao.editMentor(mentor);
        }
    }

    private void deleteMentor(){
        fetchMentor();
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id == mentor.getId()) {
            dao.deleteMentor(mentor);
        }
    }

    private void fetchMentor(){
        String id = view.getInputString("Id ?");
        mentor = dao.getMentorById(id);
        printData(mentor.getCollectedData);
    }

    private void editMentorData(){
        Map<String, String> collectedData = mentor.getCollectedData();
        for (String key : collectedData.keySet()){
            collectedData.put(key, checkForUpdate(collectedData.get(key)));
        }
        if (!confirmation()) mentor = null;
    }

    private void addRoom(){
        room = new Room(collectRoomData());
        if (room != null) dao.addRoom(room);
    }

    private void editRoom(){
        fetchRoom();
        if( room != null){
            editRoomData();
            dao.editRoom(room);
        }
    }

    private void editRoomData(){
        for (String data : room.getCollectedData()){
            checkForUpdate(data);
        }
    }

    private void deleteRoom(){
        fetchRoom();
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id == room.getId())
            dao.deleteMentor(room);
    }

    private void fetchRoom(){
        String id = view.getInputString("Id ?");
        mentor = dao.getRoomById(id);
        printData(room.getCollectedData);
    }

    private void addLevel(){
        level = new Level(collectLevelData());
    }

    private String checkForUpdate(String column){
        String option = view.getInputString(column).toUpperCase();
        if (option.equals("Y")){
            column = view.getInputString("New value?");
        }
        return column;
    }

    private void printData(Map<String, String> stringDataCollection){
        for (String key : stringDataCollection.keySet()){
            view.print(stringDataCollection.get(key));
        }
    }

    private Map<String, String> collectMentorData(){
        Map<String, String> mentorData = new HashMap<>();
        mentorData.put("email", view.getInputString("Email? "));
        mentorData.put("room", view.getInputString("Class?"));
        mentorData.put("name", view.getInputString("Name?"));
        mentorData.put("surname", view.getInputString("Surname?"));
        mentorData.put("password", view.getInputString("Name?"));
        printData(mentorData);
        if (!confirmation()) return null;
        return mentorData;
    }

    private boolean confirmation(){
        String option = view.getInputString("Confirm? (Y/N)").toUpperCase();
        if (option.equals("N")) return false;
        return true;
    }

}
