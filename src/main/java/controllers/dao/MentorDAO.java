package controllers.dao;

import models.*;
import views.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MentorDAO implements MentorDAOInterface {

    Connection connection;
    Statement stmt;
    View view = new View();

    public MentorDAO(Connection connection){
        this.connection = connection;
    }

    public void createCodecooler(CodecoolerModel cm) {
        try {
            stmt = connection.createStatement();
            String loginAccessQuery = String.format("INSERT INTO login_access (email, password, access_level) VALUES ('%s', '%s', %d );", cm.getEmail(), cm.getPassword(), 1);
            stmt.executeUpdate(loginAccessQuery);
            stmt = connection.createStatement();
            String codecoolerTableQuery = String.format("INSERT INTO codecoolers (coolcoins, exp_level, actual_room, coolcoins_ever_earned, quest_in_progress, first_name, last_name, nickname)" +
                            " VALUES ('%d', '%d', '%d', '%d', '%d', '%s', '%s', '%s');", cm.getCoolcoins(), cm.getExpLevel(), cm.getRoom(), cm.getCoolcoinsEverEarned(), cm.getQuestInProgress(),
                    cm.getFirstName(), cm.getLastName(), cm.getNickname());
            stmt.executeUpdate(codecoolerTableQuery);
            view.print("Operation done successfully\n");
            connection.commit();
            stmt.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void removeCodecooler(int codecoolerID) {
        try {
            stmt = connection.createStatement();

            String sql = String.format("DELETE FROM teams WHERE codecooler_id = %d;", codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();

            sql = String.format("DELETE FROM artifacts_in_possess WHERE codecooler_id = %d;", codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();

            sql = String.format("DELETE FROM codecoolers WHERE codecooler_id = %d;", codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();

            sql = String.format("DELETE FROM login_access WHERE id = %d;", codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();

            stmt.close();
            view.print("Operation done successfully\n");
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

    }

    public void editCodecooler(int codecoolerID, CodecoolerModel cm) {
        try {
            stmt = connection.createStatement();
            String sql = String.format("UPDATE login_access SET email = '%s', password = '%s' WHERE id = %d;", cm.getEmail(), cm.getPassword(), codecoolerID);
            stmt.executeUpdate(sql);
            sql = String.format("UPDATE codecoolers SET coolcoins = '%d', exp_level = '%d', actual_room = '%d', coolcoins_ever_earned = '%d',quest_in_progress = '%d'," +
                            "first_name = '%s', last_name = '%s', nickname = '%s' WHERE codecooler_id = %d;", cm.getCoolcoins(), cm.getExpLevel(),
                                cm.getRoom(), cm.getCoolcoinsEverEarned(), cm.getQuestInProgress(), cm.getFirstName(), cm.getLastName(), cm.getNickname(), codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();
            stmt.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> allCodecoolers(){
        List<String> codecoolers = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            String codecoolersQuery = String.format("SELECT codecooler_id, nickname FROM codecoolers;");
            ResultSet rs = stmt.executeQuery(codecoolersQuery);
            while ( rs.next() ) {
                codecoolers.add(rs.getInt(1) + " " + rs.getString(2));
            }
            rs.close();
            stmt.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return codecoolers;
    }


    public void editQuest(int quest_id, Quest editedQuest) {
        try {
            stmt = connection.createStatement();
            String sql = String.format("UPDATE quests SET name = '%s', description = '%s', reward = '%d' WHERE quest_id = %d;",
                    editedQuest.getName(), editedQuest.getDescription(), editedQuest.getReward(), quest_id);
            stmt.executeUpdate(sql);
            connection.commit();
            stmt.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> listOfQuests() {
        List<String> quests = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            String codecoolersQuery = String.format("SELECT * FROM quests;");
            ResultSet rs = stmt.executeQuery(codecoolersQuery);
            while ( rs.next() ) {
                quests.add(rs.getInt(1) + " name: " + rs.getString(2) + " description: " + rs.getString(3) + " reward: " + rs.getInt(4));
            }
            rs.close();
            stmt.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return quests;
    }

    public void addNewQuest(Quest newQuest) {
        try {
            stmt = connection.createStatement();
            String questQuery = String.format("INSERT INTO quests (name, description, reward) VALUES ('%s', '%s', %d );",
                    newQuest.getName(), newQuest.getDescription(), newQuest.getReward());
            stmt.executeUpdate(questQuery);
            view.print("Operation done successfully\n");
            connection.commit();
            stmt.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> listOfArtifactsInShop() {
        List<String> artifacts = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            String codecoolersQuery = String.format("SELECT * FROM artifacts;");
            ResultSet rs = stmt.executeQuery(codecoolersQuery);
            while ( rs.next() ) {
                artifacts.add(rs.getInt(1) + " name: " + rs.getString(2) + " description: " + rs.getString(3) + " price: " + rs.getInt(4));
            }
            rs.close();
            stmt.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return artifacts;
    }

    public void editArtifact(int artifact_id, Artifact editedArtifact) {
        try {
            stmt = connection.createStatement();
            String artifactQuery = String.format("UPDATE artifacts SET name = '%s', description = '%s', price = '%d' WHERE artifact_id = %d;",
                    editedArtifact.getName(), editedArtifact.getDescription(), editedArtifact.getPrice(), artifact_id);
            stmt.executeUpdate(artifactQuery);
            connection.commit();
            stmt.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void addArtifactToStore(Artifact newArtifact) {
        try {
            stmt = connection.createStatement();
            String artifactQuery = String.format("INSERT INTO artifacts (name, description, price) VALUES ('%s', '%s', %d );",
                    newArtifact.getName(), newArtifact.getDescription(), newArtifact.getPrice());
            stmt.executeUpdate(artifactQuery);
            view.print("Operation done successfully\n");
            connection.commit();
            stmt.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public int codecoolerCoins(int codecooler_id) {
        ResultSet resultSet;

        try{
            stmt = connection.createStatement();
            String codecoolersQuery = "SELECT coolcoins FROM codecoolers WHERE codecooler_id = " + codecooler_id + ";";
            resultSet = stmt.executeQuery(codecoolersQuery);
            while( resultSet.next() ) {
                int coins = resultSet.getInt(1);
                return coins;
            }
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return 0;
    }

    public void markQuestAsCompleted(int codecoolerID) {
        ResultSet resultSet;

        try {
            stmt = connection.createStatement();
            String codecoolersQuery = "SELECT quest_in_progress FROM codecoolers WHERE codecooler_id = " + codecoolerID + ";";
            resultSet = stmt.executeQuery(codecoolersQuery);
            int questID = resultSet.getInt(1);

            stmt = connection.createStatement();
            codecoolersQuery = String.format("UPDATE codecoolers SET quest_in_progress = 0 WHERE codecooler_id =" + codecoolerID + ";");
            stmt.executeUpdate(codecoolersQuery);

            stmt = connection.createStatement();
            String questCompletedQuery = String.format("INSERT INTO quest_completed (quest_id, codecooler_id) VALUES ('%s', '%s');", questID, codecoolerID);
            stmt.executeUpdate(questCompletedQuery);

            stmt = connection.createStatement();
            String questsQuery = "SELECT reward FROM quests WHERE quest_id = " + questID + ";";
            resultSet = stmt.executeQuery(questsQuery);
            int questReward = resultSet.getInt(1);

            stmt = connection.createStatement();
            codecoolersQuery = String.format("UPDATE codecoolers SET coolcoins = coolcoins + " + questReward + " WHERE codecooler_id =" + codecoolerID + ";");
            stmt.executeUpdate(codecoolersQuery);


            view.print("Operation done successfully\n");
            connection.commit();
            stmt.close();
        } catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public List<String> possessedArtifacts(int codecoolerID) {
        List<String> possessedArtifacts = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            String codecoolersQuery = String.format("SELECT artifacts_in_possess.artifact_id, artifacts.name FROM artifacts_in_possess " +
                    "INNER JOIN artifacts ON artifacts_in_possess.artifact_id = artifacts.artifact_id WHERE codecooler_id = %d;", codecoolerID);
            ResultSet rs = stmt.executeQuery(codecoolersQuery);
            while ( rs.next() ) {
                possessedArtifacts.add("id: " + rs.getInt(1) + " name: " + rs.getString(2));
            }
            rs.close();
            stmt.close();
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return possessedArtifacts;
    }

    public void markItemAsUsed(int artifact_id) {
        try {
            stmt = connection.createStatement();
            String artifactQuery = String.format("UPDATE artifacts_in_possess SET used = NOT used WHERE artifact_id = %d;", artifact_id);
            stmt.executeUpdate(artifactQuery);
            connection.commit();
            stmt.close();
            view.print("Operation done successfully\n");
        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }


    public MentorModel createMentor(int id) {
        return null;
    }

}
