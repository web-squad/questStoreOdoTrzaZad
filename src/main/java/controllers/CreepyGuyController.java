package controllers;

import controllers.dao.CreepyGuyDAO;
import models.Level;
import models.MentorModel;
import models.Room;
import views.View;

import java.util.HashMap;
import java.util.Map;

public class CreepyGuyController extends UserController {
    int id;
    View view;
    CreepyGuyDAO dao;
    MentorModel mentor;
    Room room;
    Level level;

    public CreepyGuyController(Integer id, CreepyGuyDAO dao){
        this.id = id;
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
        if (mentor != null) sendDeleteCommand();
    }

    private void sendDeleteCommand(){
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(mentor.getId())) {
            dao.deleteMentor(mentor);
        }
    }

    private void fetchMentor(){
        String id = view.getInputString("Id ?");
        mentor = dao.getMentorById(id);
        if (mentor != null) mentor.setId(id);
        printData(mentor.getCollectedData());
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
        editRoomData();
        if( room != null){
            dao.editRoom(room);
        }
    }

    private void editRoomData(){
        Map<String, String> collectedData = room.getCollectedData();
        for (String key : collectedData.keySet()){
            collectedData.put(key, checkForUpdate(collectedData.get(key)));
        }
        if (!confirmation()) room = null;
    }

    private void deleteRoom(){
        fetchRoom();
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(room.getId()))
            dao.deleteMentor(room);
    }

    private void fetchRoom(){
        String id = view.getInputString("Id ?");
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
            dao.editLevel(level);
        }
    }

    private void deleteLevel(){
        fetchLevel();
        String id = view.getInputString("Confirm ID");
        if (view.getInputString("Really Confirm Deleting? (Y/N)").toUpperCase().equals("Y") && id.equals(level.getId()))
            dao.deleteMentor(room);
    }

    private void fetchLevel(){
        String id = view.getInputString("Id ?");
        level = dao.getLevelById(id);
        if (level != null) level.setId(id);
        printData(room.getCollectedData());
    }

    private void editLevelData(){
        Map<String, String> collectedData = level.getCollectedData();
        for (String key : collectedData.keySet()){
            collectedData.put(key, checkForUpdate(collectedData.get(key)));
        }
        if (!confirmation()) level = null;
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

    private Map<String, String> collectRoomData(){
        Map<String, String> roomData = new HashMap<>();
        roomData.put("roomName", view.getInputString("Room name? "));
        roomData.put("roomDescription", view.getInputString("Room description? "));
        roomData.put("assignedMentor", view.getInputString("Assigned Mentor?"));
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
        if (option.equals("N")) return false;
        return true;
    }

}
