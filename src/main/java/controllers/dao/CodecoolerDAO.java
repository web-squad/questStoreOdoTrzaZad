package controllers.dao;

import models.CodecoolerModel;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;

public class CodecoolerDAO implements CodecoolerDAOInterface {
    private Connection connection;
    private Statement statement;

    CodecoolerDAO(Connection connection){
        this.connection = connection;
        statement = getStatement();
    }

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
        String loginAccesQuery = "SELECT email, password FROM LoginAccess WHERE id = " + codecoolerId + ";";
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
            int room = resultSetCodecooler.getInt(4);
            int coolCoinsEverEarned = resultSetCodecooler.getInt(5);
            int questInProgress = resultSetCodecooler.getInt(6);//robocza nazwa
            String first_name = resultSetCodecooler.getString(7);
            String second_name = resultSetCodecooler.getString(8);
            String nickName = resultSetCodecooler.getString(9);
            String email = resultSetLogin.getString(1);
            String password = resultSetLogin.getString(2);
            int teamID = resultSetTeams.getInt(1);

            codecoolerModel = new CodecoolerModel(codecoolerId, first_name, second_name, email, nickName, password,
                   1, coolcoins, expLevel, room, coolCoinsEverEarned, questInProgress, teamID)  //uporządkowane, z passwordem, chcemy password w sumie tutaj trzymać czy nie? 1 = access level



        }catch(SQLException e){
            e.printStackTrace();
        }
        return codecoolerModel;
    }

    @Override
    public int checkCoinsEverOwned(int id) {
        String coinsEverOwnedQuery = "SELECT coolcoins_ever_earned FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetCoins = getResultSet(coinsEverOwnedQuery);
        int coins  = 0;
        try{
            coins = resultSetCoins.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coins;
    } //korzystanie

    @Override
    public int checkQuestInProgress(int id) {
        String questInProgressQuery = "SELECT quest_in_progress FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetQuest = getResultSet(questInProgressQuery);
        int questId = 0;
        try{
            questId = resultSetQuest.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return questId;
    } //korzystanie

    @Override
    public int readCodecoolerClass(int id) {
        String codecoolerClassQuery = "SELECT room FROM Codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetClass = getResultSet(codecoolerClassQuery);
        int classId = 0;
        try{
            classId = resultSetClass.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return classId;
    }

    @Override
    public String readTeamName(int id) {
        String teamNameQuery = "SELECT team_name FROM Teams WHERE codecooler_id = " + id + ";";
        ResultSet resultSetTeamName = getResultSet(teamNameQuery);
        String teamName = "";
        try{
            teamName = resultSetTeamName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return teamName;
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
        String artefactsQuery = "SELECT * FROM Artifacts";
        ResultSet resultSetArtefacts = getResultSet(artefactsQuery);
        String artefacts = "";
        ResultSetMetaData resultSetMetaData;
        try{
            resultSetMetaData = resultSetArtefacts.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            for(int i = 1; i <= columnsNumber; i++){
                artefacts = artefacts + resultSetMetaData.getColumnName(i) + " ";
            }
            artefacts = artefacts + "\n";
            while(resultSetArtefacts.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if(i > 1) artefacts = artefacts + ", ";
                    artefacts = artefacts + resultSetArtefacts.getString(i);
                }
                artefacts = artefacts + "\n";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return artefacts;
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

    private Statement getStatement(){
        try{
            statement = connection.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
}
