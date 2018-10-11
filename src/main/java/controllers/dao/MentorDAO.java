package controllers.dao;

import models.*;
import views.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            String sql = String.format("DELETE FROM login_access WHERE id = %d;", codecoolerID);
            stmt.executeUpdate(sql);
            connection.commit();
            sql = String.format("DELETE FROM codecoolers WHERE id = %d;", codecoolerID);
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
            sql = String.format("UPDATE codecoolers VALUES (%d, %d, %d, %d, %d, '%s', '%s', '%s' WHERE id = %d;)", cm.getCoolcoins(), cm.getExpLevel(),
                                cm.getRoom(), cm.getCoolcoinsEverEarned(), cm.getQuestInProgress(), cm.getFirstName(), cm.getLastName(), cm.getNickname());
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
            String codecoolersQuery = String.format("SELECT id, nickname FROM codecoolers;");
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

    public List<String> codecoolerArtifacts(int codecooler_id) {
        List<String> codecoolerAndArtifacts = new ArrayList<>();
        List<Integer> artifacts = new ArrayList<>();
        try {
            Statement stmt = connection.createStatement();
            String artifactInPosses = String.format("SELECT artifact_id FROM artifact_in_posses WHERE codecooler_id = '%d'", codecooler_id);
            ResultSet rs = stmt.executeQuery(artifactInPosses);
            while ( rs.next() ) {
                artifacts.add(rs.getInt("artifact_id"));
            }
            rs.close();
            stmt.close();

            for(int i = 0; i < artifacts.size(); i++) {
                stmt = connection.createStatement();
                String artifactsQuery = String.format("SELECT name FROM artifact_in_posses WHERE artifact_id = '%d'", artifacts.get(i));
                rs = stmt.executeQuery(artifactsQuery);
            }



        }catch ( SQLException e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }

        return codecoolerAndArtifacts;
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
            String artifactQuery = String.format("UPDATE artifacts SET name = '%s', description = '%s', price = '%d' WHERE quest_id = %d;",
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
            String codecoolersQuery = "SELECT coolcoins FROM codecoolers WHERE id = " + codecooler_id + ";";
            resultSet = stmt.executeQuery(codecoolersQuery);
            int coins = resultSet.getInt(1);
            return coins;
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
            String codecoolersQuery = "SELECT quest_in_progress FROM codecoolers WHERE id = " + codecoolerID + ";";
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

    public void markItemAsUsed(int codecooler_id, int artifact_id) {
    }

    public MentorModel createMentor(int id) {
        return null;
    }
}
