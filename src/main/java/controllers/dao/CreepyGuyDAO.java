package controllers.dao;

import models.CreepyGuyModel;
import models.Level;
import models.MentorModel;
import models.Room;
import server.helpers.CookieHelper;
import views.View;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CreepyGuyDAO implements CreepyGuyDaoInterface {
    Connection connection;
    PreparedStatement ps;
    View view = new View();
    Map<String, String> mentorData;
    Map<String, String> roomData;
    Map<String, String> levelData;
    Map<String, String> creepyGuyData;

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
        ps = connection.prepareStatement("INSERT INTO login_access (email, password, access_level)"
                        + "\n VALUES (?, ?, ?);");
        ps.setString(1, mentor.getEmail());
        ps.setString(2, mentor.getPassword());
        ps.setInt(3, 2);
        ps.executeUpdate();

        ps = connection.prepareStatement("INSERT INTO codecoolers (coolcoins, exp_level, actual_room, coolcoins_ever_earned, quest_in_progress, first_name, last_name, nickname)"
                + "\n VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
        ps.setInt(1, 0);
        ps.setInt(2, 1);
        ps.setInt(3, Integer.parseInt(mentor.getRoom()));
        ps.setInt(4, 0);
        ps.setInt(5, 1);
        ps.setString(6, mentor.getName());
        ps.setString(7, mentor.getSurname());
        ps.setString(8, mentor.getNickName());
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        ps = connection.prepareStatement("Update login_access SET email = ?, password = ? WHERE id = ?;");
        ps.setString(1, mentor.getEmail());
        ps.setString(2, mentor.getPassword());
        ps.setInt(3, Integer.parseInt(id));
        ps.executeUpdate();

        ps = connection.prepareStatement("Update codecoolers SET coolcoins = ?, exp_level = ?, actual_room = ?, " +
                "coolcoins_ever_earned = ?, quest_in_progress = ?, first_name = ?, last_name = ?, nickname = ? WHERE codecooler_id = ?;");
        ps.setInt(1, 0);
        ps.setInt(2, 1);
        ps.setInt(3, Integer.parseInt(mentor.getRoom()));
        ps.setInt(4, 0);
        ps.setInt(5, 1);
        ps.setString(6, mentor.getName());
        ps.setString(7, mentor.getSurname());
        ps.setString(8, mentor.getNickName());
        ps.setInt(9, Integer.parseInt(id));
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        ps = connection.prepareStatement("DELETE FROM codecoolers WHERE codecooler_id = ?;");
        ps.setInt(1, Integer.parseInt(mentor.getId()));
        ps.executeUpdate();

        ps = connection.prepareStatement("DELETE FROM login_access WHERE id = ?;");
        ps.setInt(1, Integer.parseInt(mentor.getId()));
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        }catch (Exception e){
            return null;
        }
        return null;
    }

    private void fetchMentor(String id) throws Exception{
        mentorData = new HashMap<>();
        ps = connection.prepareStatement("SELECT login_access.email, login_access.access_level, codecoolers.first_name, codecoolers.nickname, " +
                "codecoolers.last_name, login_access.password, codecoolers.actual_room FROM login_access " +
                " INNER JOIN codecoolers ON login_access.id = codecoolers.codecooler_id WHERE id = ?;");
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            mentorData.put("firstName", rs.getString("first_name"));
            mentorData.put("surname", rs.getString("last_name"));
            mentorData.put("email", rs.getString("email"));
            mentorData.put("password", rs.getString("password"));
            mentorData.put("nickName", rs.getString("nickname"));
            mentorData.put("room", String.valueOf(rs.getInt("actual_room")));
            if(rs.getInt("access_level") != 2){
                throw new Exception();
            }
        }
        rs.close();
        ps.close();
    }

    public CreepyGuyModel getAdminBySessionId(String id){
        try {
            fetchAdmin(id);
            view.print("Operation done successfully\n");
            return new CreepyGuyModel(creepyGuyData);
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        } catch (NumberFormatException e){
            view.print("Passed ID is not numerical value");
        }catch (Exception e){
            return null;
        }
        return null;
    }

    private void fetchAdmin(String sessionId) throws Exception{
        creepyGuyData = new HashMap<>();
        ps = connection.prepareStatement("SELECT login_access.email, login_access.access_level, codecoolers.first_name, codecoolers.nickname, " +
                "codecoolers.last_name, login_access.password FROM login_access " +
                " INNER JOIN codecoolers ON login_access.id = codecoolers.codecooler_id WHERE session_id = ?;");
        ps.setString(1, sessionId);
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            creepyGuyData.put("firstName", rs.getString("first_name"));
            creepyGuyData.put("surname", rs.getString("last_name"));
            creepyGuyData.put("email", rs.getString("email"));
            creepyGuyData.put("password", rs.getString("password"));
            creepyGuyData.put("nickName", rs.getString("nickname"));
            if(rs.getInt("access_level") != 3){
                throw new Exception();
            }
        }
        rs.close();
        ps.close();
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
        ps = connection.prepareStatement("INSERT INTO room ( room_description, room_name) VALUES (?, ?);");
        ps.setString(1, room.getRoomDescription());
        ps.setString(2, room.getRoomName());
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        ps = connection.prepareStatement("Update room SET room_name = ?, room_description = ?\n" +
                        " WHERE room_id = ?;");
        ps.setString(1, room.getRoomName());
        ps.setString(2, room.getRoomDescription());
        ps.setInt(3, Integer.parseInt(id));
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        ps = connection.prepareStatement("DELETE FROM room WHERE room_id = ?;");
        ps.setInt(1, Integer.parseInt(room.getId()));
        ps.executeUpdate();
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
        System.out.println(id);
        ps = connection.prepareStatement("SELECT * FROM room WHERE room_id = ?;");
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            roomData.put("roomName", rs.getString("room_name"));
            roomData.put("roomDescription", rs.getString("room_description"));
        }
        rs.close();
        ps.close();
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
        ps = connection.prepareStatement("INSERT INTO experience_level ( level, threshold) VALUES (?, ?);");
        ps.setString(1, level.getLevelName());
        ps.setInt(2, Integer.parseInt(level.getThreshold()));
        ps.executeUpdate();
        connection.commit();
        ps.close();
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
        ps = connection.prepareStatement("Update experience_level SET level = ?, threshold = ? WHERE id = ?;");
        ps.setString(1, level.getLevelName());
        ps.setInt(2, Integer.parseInt(level.getThreshold()));
        ps.setInt(3, Integer.parseInt(id));
        ps.executeUpdate();
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
        ps = connection.prepareStatement("SELECT * FROM experience_level WHERE id = ?;");
        ps.setInt(1, Integer.parseInt(id));
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) {
            mentorData.put("levelName", rs.getString("level"));
            mentorData.put("threshold", String.valueOf(rs.getInt("threshold")));
        }
        rs.close();
        ps.close();
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
        ps = connection.prepareStatement("DELETE FROM experience_level WHERE id = ?;");
        ps.setInt(1, Integer.parseInt(level.getId()));
        ps.executeUpdate();
    }
}

