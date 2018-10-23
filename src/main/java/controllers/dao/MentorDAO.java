package controllers.dao;

import models.*;
import views.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MentorDAO implements MentorDAOInterface {

    Connection connection;
    PreparedStatement ps;
    View view = new View();

    public MentorDAO(Connection connection){
        this.connection = connection;
    }

    public void createCodecooler(CodecoolerModel cm) {
        try {
            ps = connection.prepareStatement("INSERT INTO login_access (email, password, access_level) VALUES (?, ?, ?);");
            ps.setString(1, cm.getEmail());
            ps.setString(2, cm.getPassword());
            ps.setInt(3, 1);
            ps.executeUpdate();

            ps = connection.prepareStatement("INSERT INTO codecoolers (coolcoins, exp_level, actual_room, coolcoins_ever_earned, quest_in_progress, first_name, last_name, nickname)" +
                            " VALUES (?, ?, ?, ?, ?, ?, ?, ?);");
            ps.setInt(1, cm.getCoolcoins());
            ps.setInt(2, cm.getExpLevel());
            ps.setInt(3, cm.getRoom());
            ps.setInt(4, cm.getCoolcoinsEverEarned());
            ps.setInt(5, cm.getQuestInProgress());
            ps.setString(6, cm.getFirstName());
            ps.setString(7, cm.getLastName());
            ps.setString(8, cm.getNickname());
            ps.executeUpdate();

            ps = connection.prepareStatement("SELECT id FROM login_access WHERE email = ?;");
            ps.setString(1, cm.getEmail());
            ResultSet rs = ps.executeQuery();
            int codecooler_id = 0;
            while( rs.next() ) {
                codecooler_id = rs.getInt(1);
            }

            ps = connection.prepareStatement("INSERT INTO teams (codecooler_id, team_name) VALUES (?, ?)");
            ps.setInt(1, codecooler_id);
            ps.setString(2, "szpoki");
            ps.executeUpdate();

            view.print("Operation done successfully\n");
            connection.commit();
            ps.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void removeCodecooler(int codecoolerID) {
        try {
            ps = connection.prepareStatement("DELETE FROM quest_completed WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM teams WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM artifacts_in_possess WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM codecoolers WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM login_access WHERE id = ?;");
            ps.setInt(1, codecoolerID);
            ps.executeUpdate();
            connection.commit();

            ps.close();
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public void editCodecooler(int codecoolerID, CodecoolerModel cm) {
        try {
            ps = connection.prepareStatement("UPDATE login_access SET email = ?, password = ? WHERE id = ?;");
            ps.setString(1, cm.getEmail());
            ps.setString(2, cm.getPassword());
            ps.setInt(3, codecoolerID);
            ps.executeUpdate();
            ps = connection.prepareStatement("UPDATE codecoolers SET coolcoins = ?, exp_level = ?, actual_room = ?, coolcoins_ever_earned = ?,quest_in_progress = ?," +
                            "first_name = ?, last_name = ?, nickname = ? WHERE codecooler_id = ?;");
            ps.setInt(1, cm.getCoolcoins());
            ps.setInt(2, cm.getExpLevel());
            ps.setInt(3, cm.getRoom());
            ps.setInt(4, cm.getCoolcoinsEverEarned());
            ps.setInt(5, cm.getQuestInProgress());
            ps.setString(6, cm.getFirstName());
            ps.setString(7, cm.getLastName());
            ps.setString(8, cm.getNickname());
            ps.setInt(9, codecoolerID);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> allCodecoolers(){
        List<String> codecoolers = new ArrayList<>();
        try {
            ps = connection.prepareStatement("SELECT codecooler_id, nickname FROM codecoolers;");
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                codecoolers.add(rs.getInt(1) + " " + rs.getString(2));
            }
            rs.close();
            ps.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return codecoolers;
    }


    public void editQuest(int quest_id, Quest editedQuest) {
        try {
            ps = connection.prepareStatement("UPDATE quests SET name = ?, description = ?, reward = ? WHERE quest_id = ?;");
            ps.setString(1, editedQuest.getName());
            ps.setString(2, editedQuest.getDescription());
            ps.setInt(3, editedQuest.getReward());
            ps.setInt(4, quest_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> listOfQuests() {
        List<String> quests = new ArrayList<>();
        try {
            ps = connection.prepareStatement("SELECT * FROM quests;");
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                quests.add(rs.getInt(1) + " name: " + rs.getString(2) + " description: " + rs.getString(3) + " reward: " + rs.getInt(4));
            }
            rs.close();
            ps.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return quests;
    }

    public void addNewQuest(Quest newQuest) {
        try {
            ps = connection.prepareStatement("INSERT INTO quests (name, description, reward) VALUES (?, ?, ?);");
            ps.setString(1, newQuest.getName());
            ps.setString(2, newQuest.getDescription());
            ps.setInt(3, newQuest.getReward());
            ps.executeUpdate();
            view.print("Operation done successfully\n");
            connection.commit();
            ps.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> listOfArtifactsInShop() {
        List<String> artifacts = new ArrayList<>();
        try {
            ps = connection.prepareStatement("SELECT * FROM artifacts;");
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                artifacts.add(rs.getInt(1) + " name: " + rs.getString(2) + " description: " + rs.getString(3) + " price: " + rs.getInt(4));
            }
            rs.close();
            ps.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return artifacts;
    }

    public void editArtifact(int artifact_id, Artifact editedArtifact) {
        try {
            ps = connection.prepareStatement("UPDATE artifacts SET name = ?, description = ?, price = ? WHERE artifact_id = ?;");
            ps.setString(1, editedArtifact.getName());
            ps.setString(2, editedArtifact.getDescription());
            ps.setInt(3, editedArtifact.getPrice());
            ps.setInt(4, artifact_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void addArtifactToStore(Artifact newArtifact) {
        try {
            ps = connection.prepareStatement("INSERT INTO artifacts (name, description, price) VALUES (?, ?, ?);");
            ps.setString(1, newArtifact.getName());
            ps.setString(2, newArtifact.getDescription());
            ps.setInt(3, newArtifact.getPrice());
            ps.executeUpdate();
            view.print("Operation done successfully\n");
            connection.commit();
            ps.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public int codecoolerCoins(int codecooler_id) {
        ResultSet resultSet;

        try{
            ps = connection.prepareStatement("SELECT coolcoins FROM codecoolers WHERE codecooler_id = ?;");
            ps.setInt(1, codecooler_id);
            resultSet = ps.executeQuery();
            while( resultSet.next() ) {
                int coins = resultSet.getInt(1);
                return coins;
            }
            resultSet.close();
            ps.close();
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return 0;
    }

    public void markQuestAsCompleted(int codecoolerID) {
        ResultSet resultSet;

        try {
            ps = connection.prepareStatement("SELECT quest_in_progress FROM codecoolers WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            resultSet = ps.executeQuery();
            int questID = resultSet.getInt(1);

            ps = connection.prepareStatement("UPDATE codecoolers SET quest_in_progress = 0 WHERE codecooler_id = ?;");
            ps.executeUpdate();

            ps = connection.prepareStatement("INSERT INTO quest_completed (quest_id, codecooler_id) VALUES (?, ?);");
            ps.setInt(1, questID);
            ps.setInt(2, codecoolerID);
            ps.executeUpdate();

            ps = connection.prepareStatement("SELECT reward FROM quests WHERE quest_id = ?;");
            ps.setInt(1, questID);
            resultSet = ps.executeQuery();
            int questReward = resultSet.getInt(1);

            ps = connection.prepareStatement("UPDATE codecoolers SET coolcoins = coolcoins + ? WHERE codecooler_id = ?;");
            ps.setInt(1, questReward);
            ps.setInt(2, codecoolerID);
            ps.executeUpdate();


            view.print("Operation done successfully\n");
            connection.commit();
            resultSet.close();
            ps.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> possessedArtifacts(int codecoolerID) {
        List<String> possessedArtifacts = new ArrayList<>();
        try {
            ps = connection.prepareStatement("SELECT artifacts_in_possess.artifact_id, artifacts.name FROM artifacts_in_possess " +
                    "INNER JOIN artifacts ON artifacts_in_possess.artifact_id = artifacts.artifact_id WHERE codecooler_id = ?;");
            ps.setInt(1, codecoolerID);
            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                possessedArtifacts.add("id: " + rs.getInt(1) + " name: " + rs.getString(2));
            }
            rs.close();
            ps.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return possessedArtifacts;
    }

    public void markItemAsUsed(int artifact_id) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE artifacts_in_possess SET used = NOT used WHERE artifact_id = ?;");
            ps.setInt(1, artifact_id);
            ps.executeUpdate();
            connection.commit();
            ps.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> searchForStudent(String word) {
        List<String> searchResult = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT codecooler_id, first_name, last_name FROM codecoolers INNER JOIN login_access " +
                    "ON codecoolers.codecooler_id = login_access.id WHERE (first_name LIKE ? AND access_level = 1) OR (last_name LIKE ? AND access_level = 1) " +
                    "OR (nickname LIKE ? AND access_level = 1) OR (email LIKE ? AND access_level = 1);");
            ps.setString(1, '%' + word + '%');
            ps.setString(2, '%' + word + '%');
            ps.setString(3, '%' + word + '%');
            ps.setString(4, '%' + word + '%');

            ResultSet rs = ps.executeQuery();
            while ( rs.next() ) {
                searchResult.add("ID:" + rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(2));
            }
            rs.close();
            ps.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return searchResult;
    }
}

