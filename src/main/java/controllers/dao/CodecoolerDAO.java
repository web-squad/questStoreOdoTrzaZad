package controllers.dao;

import models.CodecoolerModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CodecoolerDAO  {
    private Connection connection;
    private Statement statement;

    CodecoolerDAO(Connection connection) throws SQLException {
        this.connection = connection;
    }

} /*
    @Override
    public int readCoins(int codecoolerId) {
        String query = "SELECT coolcoins FROM codecoolers WHERE id = " + codecoolerId + ";";
        ResultSet resultSet = getResultSet(query);
        int coins = 0;
        try{
            coins = resultSet.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coins;
    }

    public CodecoolerModel getCodecoolerModel(int codecoolerId){
        String codecoolerTableQuery = "SELECT * FROM Codecoolers WHERE codecooler_id = " + codecoolerId + ";";
        String teamsTableQuery = "SELECT team_name FROM Teams WHERE codecooler_id = " + codecoolerId + ";";
        String loginAccesQuery = "SELECT email FROM LoginAccess WHERE id = " + codecoolerId + ";";
        ResultSet resultSetCodecooler = null;
        ResultSet resultSetTeams = null;
        ResultSet resultSetLogin = null;
        CodecoolerModel codecoolerModel = null;
        try{
            resultSetCodecooler = statement.executeQuery(codecoolerTableQuery);
            resultSetTeams = statement.executeQuery(teamsTableQuery);
            resultSetLogin = statement.executeQuery(loginAccesQuery);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        try{
            int coolcoins = resultSetCodecooler.getInt(2);
            int expLevel = resultSetCodecooler.getInt(3);//robocza nazwa
            String room = resultSetCodecooler.getString(4);
            int coolCoinsEverEarned = resultSetCodecooler.getInt(5);
            String questInProgress = resultSetCodecooler.getString(6);//robocza nazwa
            String first_name = resultSetCodecooler.getString(7);
            String second_name = resultSetCodecooler.getString(8);
            String nickName = resultSetCodecooler.getString(9);
            String email = resultSetLogin.getString(1);
            String teamID = resultSetTeams.getString(1);
            codecoolerModel = new CodecoolerModel(codecoolerId, coolcoins, expLevel, room, coolCoinsEverEarned, questInProgress, first_name, second_name, nickName, email,teamID);

        }catch(SQLException e){
            e.printStackTrace();
        }
        return codecoolerModel;
    }

    @Override
    public int checkCoinsEverOwned(int id) {
        return 0;
    } //korzystanie

    @Override
    public String checkQuestInProgress(int id) {
        return null;
    } //korzystanie

    @Override
    public String readCodecoolerClass(int id) {
        return null;
    }

    @Override
    public String readTeamName(int id) {
        return null;
    } //korzystanie

    @Override
    public String getNickName(int id) {
        return null;
    }

    @Override
    public String getFirstName(int id) {
        return null;
    }

    @Override
    public String getSecondName(int id) {
        return null;
    }

    @Override
    public String getEmail(int id) {
        return null;
    } //korzystanie

    @Override
    public String readArtefacts() {
        return null;
    } //korzystanie

    @Override
    public String readEmailsCoolcoinsAndArtefacts(int codecoolerId) {

        return null;
    } //korzystnaie

    @Override
    public int getPriceOfArtefact(int artefactId) {
        String artefactsTableQuery = "SELECT price FROM Artifacts WHERE artifact_id = " + artefactId + ";";
        ResultSet resultSetArtefacts = getResultSet(artefactsTableQuery);
        int price = 0;
        try{
            price = resultSetArtefacts.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return price;

    } //musthave

    @Override
    public void addNewPossesion(int codecoolerId, int artefactId) { //musthave
        String addPossesionQuery = "INSERT INTO ArtifactsInPossess (artifact_id, codecooler_id VALUES (\"" + artefactId +"\", \"" + codecoolerId +"\")";
        try{
            statement.executeQuery(addPossesionQuery);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Integer> readTeamMembersId(int artefactId) {
        return null;
    } //musthave

    @Override
    public void subtractCodecoolersCoolcoins(int codecoolerId, int artefactPrice) {
        int coins = readCoins(codecoolerId) - artefactPrice;
        String updateCoinsQuery = "UPDATE Codecoolers SET coolcoins = " + coins + ";";
        try{
            statement.executeQuery(updateCoinsQuery);
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    private ResultSet getResultSet(String query){
        ResultSet resultSet = null;
        try{
            resultSet = statement.executeQuery(query);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        return resultSet;
    }
} /*/
