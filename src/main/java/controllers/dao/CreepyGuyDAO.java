package controllers.dao;

import models.Level;
import models.MentorModel;
import models.Room;
import views.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class CreepyGuyDAO implements CreepyGuyDaoInterface {
    Connection connection;
    Statement stmt;
    View view = new View();
    Map<String, String> mentorData;
    Map<String, String> roomData;
    Map<String, String> levelData;

    public CreepyGuyDAO(Connection connection){
        this.connection = connection;
    }

    public void addMentor(MentorModel mentor) throws NumberFormatException{
        try {
            addMentorRecord(mentor);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    private void addMentorRecord(MentorModel mentor) throws SQLException, NumberFormatException{
        stmt = connection.createStatement();
        String sql = String.format("INSERT INTO login_access (email, password, access_level)"
                        + "\n VALUES ('%s', '%s', %d );", mentor.getEmail(), mentor.getPassword(), 1);
        stmt.executeUpdate(sql);
        sql = String.format("INSERT INTO codecoolers (coolcoins, exp_level, actual_room, coolcoins_ever_earned, quest_in_progress, first_name, last_name, nickname)"
                + "\n VALUES (%d, %d, %d, %d, %d, '%s', '%s', '%s');", 0, 1, Integer.parseInt(mentor.getRoom()), 0, 0, mentor.getName(), mentor.getSurname(), mentor.getNickName());
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public void editMentor(MentorModel mentor, String id){
        try {
            mentorUpdateProcessing(mentor, id);
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    private void mentorUpdateProcessing(MentorModel mentor, String id)throws SQLException{
        stmt = connection.createStatement();
        String sql = String.format("Update login_access"
                        + "\n SET email = '%s', password = '%s'\n" +
                        " WHERE id = %d;",
                mentor.getEmail(), mentor.getPassword(), Integer.parseInt(id));
        stmt.executeUpdate(sql);
        sql = String.format("Update codecoolers"
                + "\n SET coolcoins = %d, exp_level = %d, actual_room = %d, coolcoins_ever_earned = %d, quest_in_progress = %d, first_name = '%s', last_name = '%s', nickname ='%s' WHERE codecooler_id = %d;",
                0, 1, Integer.parseInt(mentor.getRoom()), 0, 0, mentor.getName(), mentor.getSurname(), mentor.getNickName(), Integer.parseInt(id));
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public void deleteMentor(MentorModel mentor) {
        try {
            deleteRecord(mentor);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    private void deleteRecord(MentorModel mentor) throws SQLException{
        stmt = connection.createStatement();
        String sql = String.format("DELETE FROM login_access WHERE id = %d;", Integer.parseInt(mentor.getId()));
        stmt.executeUpdate(sql);
        connection.commit();
        sql = String.format("DELETE FROM codecoolers WHERE id = %d;", Integer.parseInt(mentor.getId()));
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public MentorModel getMentorById(String id){
        try {
            fetchMentor(id);
            view.print("Operation done successfully\n");
            return new MentorModel(mentorData);
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } catch (NumberFormatException e){
        view.print("Passed ID is not numerical value");
        }
        return null;
    }

    private void fetchMentor(String id) throws SQLException, NumberFormatException{
        mentorData = new HashMap<>();
        stmt = connection.createStatement();
        String sql = String.format("SELECT login_access.email, codecoolers.first_name, codecoolers.nickname, " +
                "codecoolers.last_name, login_access.password, codecoolers.actual_room FROM login_access " +
                " INNER JOIN codecoolers ON login_access.id = codecoolers.codecooler_id WHERE id = %d;", Integer.parseInt(id));
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            mentorData.put("firstName", rs.getString("first_name"));
            mentorData.put("surname", rs.getString("last_name"));
            mentorData.put("email", rs.getString("email"));
            mentorData.put("password", rs.getString("password"));
            mentorData.put("nickName", rs.getString("nickname"));
            mentorData.put("room", String.valueOf(rs.getInt("actual_room")));
        }
        rs.close();
        stmt.close();
    }

    public void addRoom(Room room){
        try {
            addRoomRecord(room);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    private void addRoomRecord(Room room) throws SQLException{
        stmt = connection.createStatement();
        String sql = String.format("INSERT INTO room ( room_description, room_name)"
                + "\n VALUES ('%s', '%s');", room.getRoomDescription(), room.getRoomName());
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public void editRoom(Room room, String id){
        try {
            editRoomRecord(room, id);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void editRoomRecord(Room room, String id) throws SQLException{
        stmt = connection.createStatement();
        System.out.println(room.getRoomDescription());
        String sql = String.format("Update room"
                        + "\n SET room_name = '%s', room_description = '%s'\n" +
                        " WHERE room_id = %d;",
                room.getRoomName(), room.getRoomDescription(), Integer.parseInt(id));
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public void deleteRoom(Room room){
        try {
            deleteRoomRecord(room);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void deleteRoomRecord(Room room) throws SQLException{
        stmt = connection.createStatement();
        String sql = String.format("DELETE FROM room" +
                        " WHERE room_id = %d;",
                Integer.parseInt(room.getId()));
        stmt.executeUpdate(sql);
        connection.commit();
    }

    public Room getRoomById(String id){
        try{
            fetchRoom(id);
            view.print("Operation done successfully\n");
            return new Room(roomData);
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }catch (NumberFormatException e){
            view.print("Id passed not a numerical value");
        }
        return null;
    }

    private void fetchRoom(String id) throws SQLException, NumberFormatException{
        roomData = new HashMap<>();
        stmt = connection.createStatement();
        System.out.println(id);
        String sql = String.format("SELECT * FROM room WHERE room_id = %d;", Integer.parseInt(id));
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            roomData.put("roomName", rs.getString("room_name"));
            roomData.put("roomDescription", rs.getString("room_description"));
        }
        rs.close();
        stmt.close();
    }

    public void addLevel(Level level) {
        try {
            addLevelRecord(level);
            view.print("Operation done successfully\n");
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        } catch (NumberFormatException e) {
            view.print("Threshold is not numerical value");
        }
    }


    private void addLevelRecord(Level level) throws SQLException, NumberFormatException{
        stmt = connection.createStatement();
        String sql = String.format("INSERT INTO experience_level ( level, threshold)"
                + "\n VALUES ('%s', '%d');", level.getLevelName(), Integer.parseInt(level.getThreshold()));
        stmt.executeUpdate(sql);
        connection.commit();
        stmt.close();
    }

    public void editLevel(Level level, String id){
        try {
            editLevelRecord(level, id);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } catch (NumberFormatException e){
            view.print("Threshold is not numerical value");
        }
    }

    private void editLevelRecord(Level level, String id) throws SQLException, NumberFormatException{
        stmt = connection.createStatement();
        String sql = String.format("Update experience_level"
                        + "\n SET level = '%s', threshold = d\n" +
                        "WHERE id = %d;",
                level.getLevelName(), Integer.parseInt(level.getThreshold()), Integer.parseInt(id));
        stmt.executeUpdate(sql);
    }

    public Level getLevelById(String id){
        try{
            fetchLevel(id);
            view.print("Operation done successfully\n");
            return new Level(levelData);
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }catch (NumberFormatException e){
            view.print("Threshold passed is not numerical value");
        }
        return null;
    }

    private void fetchLevel(String id)throws SQLException, NumberFormatException{
        levelData = new HashMap<>();
        stmt = connection.createStatement();
        String sql = String.format("SELECT * FROM experience_level WHERE id = %d;", Integer.parseInt(id));
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            mentorData.put("levelName", rs.getString("level"));
            mentorData.put("threshold", String.valueOf(rs.getInt("threshold")));
        }
        rs.close();
        stmt.close();
    }

    public void deleteLevel(Level level){
        try {
            deleteLevelRecord(level);
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    private void deleteLevelRecord(Level level) throws SQLException {
        stmt = connection.createStatement();
        String sql = String.format("DELETE FROM experience_level" +
                        "WHERE id = %d;",
                Integer.parseInt(level.getId()));
        stmt.executeUpdate(sql);
    }
}

