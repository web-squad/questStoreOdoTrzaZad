package controllers.dao;

import models.Level;
import models.MentorModel;
import models.Room;

public interface CreepyGuyDaoInterface {

    void addMentor(MentorModel mentor);
    void editMentor(MentorModel mentor, String id);
    void deleteMentor(String id);

    void addRoom(Room room);
    void editRoom(Room room, String id);
    void deleteRoom(String id);

    void addLevel(Level level);
    void editLevel(Level level, String id);
    void deleteLevel(String levelId);

    MentorModel getMentorById(String id);
    Room getRoomById(String id);
    Level getLevelById(String id);
}
