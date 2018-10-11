package controllers;

import controllers.dao.CreepyGuyDAO;
import models.Level;
import models.MentorModel;
import models.Room;
import views.View;

import java.util.HashMap;
import java.util.Map;

public class CreepyGuyController extends UserController {
    String id;
    View view;
    CreepyGuyDAO dao;
    MentorModel mentor;
    Room room;
    Level level;
    Map<String, String> collectedData;

    public CreepyGuyController(Integer id, CreepyGuyDAO dao){
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
                    deleteRoom();
                    break;
                case 3:
                    editRoom();
                    break;
            }
        }
    }

    private void experienceLevelManager(){

        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Quit",
                    "Add Level",
                    "Edit Level",
                    "Delete Level");

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
        try {
            if (mentor != null) dao.addMentor(mentor);
        }catch (NumberFormatException e){
            view.print("Wrong format for room number");
        }
    }

    private void editMentor(){
        fetchMentor();
        editMentorData();
        if (mentor != null) {
            dao.editMentor(mentor, id);
        }
    }

    private void deleteMentor(){
        fetchMentor();
        if (mentor != null) sendDeleteCommand();
    }

    private void sendDeleteCommand(){
        id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(mentor.getId())) {
            dao.deleteMentor(mentor);
        }
    }

    private void fetchMentor(){
        id = view.getInputString("Id ?");
        mentor = dao.getMentorById(id);
        if (mentor != null) {
            mentor.setId(id);
            printData(mentor.getCollectedData());
        }
    }

    private void editMentorData(){
        collectedData = mentor.getCollectedData();
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
        editRoomData();
        if( room != null){
            dao.editRoom(new Room(collectedData), id);
        }
    }

    private void editRoomData(){
        collectedData = room.getCollectedData();
        for (String key : collectedData.keySet()){
            collectedData.put(key, checkForUpdate(collectedData.get(key)));
        }
        if (!confirmation()) room = null;

    }

    private void deleteRoom(){
        fetchRoom();
        id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(room.getId()))
            System.out.println();
            dao.deleteRoom(room);
    }

    private void fetchRoom(){
        id = view.getInputString("Id ?");
        room = dao.getRoomById(id);
        if (room != null) room.setId(id);
        printData(room.getCollectedData());
    }

    private void addLevel(){
        level = new Level(collectLevelData());
        if (level != null) dao.addLevel(level);
    }

    private void editLevel(){
        fetchLevel();
        editLevelData();
        if (level !=null);{
            System.out.println();
            dao.editLevel(level, id);
        }
    }

    private void deleteLevel(){
        fetchLevel();
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(level.getId()))
            dao.deleteLevel(level);
    }

    private void fetchLevel(){
        id = view.getInputString("Id ?");
        level = dao.getLevelById(id);
        if (level != null) level.setId(id);
        printData(room.getCollectedData());
    }

    private void editLevelData(){
        collectedData = level.getCollectedData();
        for (String key : collectedData.keySet()){
            collectedData.put(key, checkForUpdate(collectedData.get(key)));
        }
        if (!confirmation()) level = null;

    }

    private String checkForUpdate(String column){
        String option = view.getInputString(column + "  Update? (Y/N)").toUpperCase();
        if (option.equals("Y")){
            column = view.getInputString("New value?");
        }
        return column;
    }

    private void printData(Map<String, String> stringDataCollection){
        for (String key : stringDataCollection.keySet()){
            view.print(stringDataCollection.get(key) + "\n");
        }
    }

    private Map<String, String> collectMentorData(){
        Map<String, String> mentorData = new HashMap<>();
        mentorData.put("email", view.getInputString("Email? "));
        mentorData.put("room", view.getInputString("Class?"));
        mentorData.put("name", view.getInputString("Name?"));
        mentorData.put("surname", view.getInputString("Surname?"));
        mentorData.put("password", view.getInputString("Password?"));
        mentorData.put("nickName", view.getInputString("Nick Name?"));
        printData(mentorData);
        if (!confirmation()) return null;
        return mentorData;
    }

    private Map<String, String> collectRoomData(){
        Map<String, String> roomData = new HashMap<>();
        roomData.put("roomName", view.getInputString("Room name? "));
        roomData.put("roomDescription", view.getInputString("Room description? "));
        printData(roomData);
        if (!confirmation()) return null;
        return roomData;
    }

    private Map<String, String> collectLevelData(){
        Map<String, String> roomData = new HashMap<>();
        roomData.put("levelName", view.getInputString("Level name? "));
        roomData.put("threshold", view.getInputString("Threshold? "));
        printData(roomData);
        if (!confirmation()) return null;
        return roomData;
    }

    private boolean confirmation(){
        String option = view.getInputString("Confirm? (Y/N)").toUpperCase();
        if (option.equals("Y")) return true;
        return false;
    }

}
