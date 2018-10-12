package controllers.dao;

import models.CodecoolerModel;
import models.Artifact;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerDAO implements CodecoolerDAOInterface {
    private Connection connection;
    private Statement statement;

    public CodecoolerDAO(Connection connection){
        this.connection = connection;
        getStatement();
    }

    @Override
    public int readCoins(int codecoolerId) {

        String query = "SELECT coolcoins FROM codecoolers WHERE codecooler_id = " + codecoolerId + ";";
        ResultSet resultSet = getResultSet(query);
        int coins = 0;
        try{
            while (resultSet.next()) {
                coins = resultSet.getInt("coolcoins");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
//18:30 wtorek5
        return coins;
    }

    public CodecoolerModel getCodecoolerModel(int codecoolerId){
        String codecoolerModelQuery = String.format("SELECT codecoolers.*, teams.id, login_access.email, login_access.password FROM codecoolers INNER JOIN" +
                " teams ON codecoolers.codecooler_id = teams.codecooler_id INNER JOIN login_access ON codecoolers.codecooler_id = login_access.id WHERE" +
                " codecoolers.codecooler_id = %d;", codecoolerId);
        ResultSet resultSetModel = null;
        CodecoolerModel codecoolerModel = null;

        try{
            resultSetModel = statement.executeQuery(codecoolerModelQuery);
        }catch(SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("Couldn't find selected query");
        }
        try{
            while(resultSetModel.next()){
                int coolcoins = resultSetModel.getInt("coolcoins");
                int expLevel = resultSetModel.getInt("exp_level");//robocza nazwa
                int room = resultSetModel.getInt("actual_room");
                int coolCoinsEverEarned = resultSetModel.getInt("coolcoins_ever_earned");
                int questInProgress = resultSetModel.getInt("quest_in_progress");//robocza nazwa
                String first_name = resultSetModel.getString("first_name");
                String second_name = resultSetModel.getString("last_name");
                String nickName = resultSetModel.getString("nickname");
                int teamID = resultSetModel.getInt("id");
                String email = resultSetModel.getString("email");
                String password = resultSetModel.getString("password");

                codecoolerModel = new CodecoolerModel(codecoolerId, first_name, second_name, email, nickName, password,
                        1, coolcoins, expLevel, room, coolCoinsEverEarned, questInProgress, teamID);
            }


            //uporządkowane, z passwordem, chcemy password w sumie tutaj trzymać czy nie? 1 = access level

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
        String nickNameQuery = "SELECT nickname FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetNickName = getResultSet(nickNameQuery);
        String nickName = "";
        try{
            nickName = resultSetNickName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return nickName;
    }

    @Override
    public String getFirstName(int id) {
        String firstNameQuery = "SELECT name FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetName = getResultSet(firstNameQuery);
        String firstName = "";
        try{
            firstName = resultSetName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return firstName;
    }

    @Override
    public String getSecondName(int id) {
        String secondNameQuery = "SELECT last_name FROM codecoolers WHERE codecooler_id = " + id + ";";
        ResultSet resultSetSecondName = getResultSet(secondNameQuery);
        String secondName = "";
        try{
            secondName = resultSetSecondName.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return secondName;
    }

    @Override
    public String getEmail(int id) {
        String emailQuery = "SELECT email FROM LoginAccess WHERE id = " + id + ";";
        ResultSet resultSetEmail = getResultSet(emailQuery);
        String email = "";
        try{
            email = resultSetEmail.getString(1);
        }catch(SQLException e){
            e.printStackTrace();
        }
        return email;
    } //korzystanie

    @Override
    public String readArtifacts() {
        String artifactsQuery = "SELECT * FROM Artifacts";
        ResultSet resultSetArtefacts = getResultSet(artifactsQuery);
        String artifacts = "";
        ResultSetMetaData resultSetMetaData;
        try{
            resultSetMetaData = resultSetArtefacts.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            for(int i = 1; i <= columnsNumber; i++){
                artifacts = artifacts + resultSetMetaData.getColumnName(i) + " ";
            }
            artifacts = artifacts + "\n";
            while(resultSetArtefacts.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if(i > 1) artifacts = artifacts + ", ";
                    artifacts = artifacts + resultSetArtefacts.getString(i);
                }
                artifacts = artifacts + "\n";
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return artifacts;
    } //korzystanie

    @Override
    public List<Artifact> readCodecoolersArtifacts(int codecoolerId) {
        String artifactsInPossessQuery = "SELECT artifact_id FROM artifacts_in_possess WHERE codecooler_id = " + codecoolerId + ";";
        String artifactsQuery = "SELECT * FROM artifacts ";
        List<Artifact> artifactsList;
        ResultSet resultSetArtifactsPossessed = getResultSet(artifactsInPossessQuery);

        String whereClauseIds = createWhereWithPossessedArtifacts(resultSetArtifactsPossessed);

        artifactsQuery += whereClauseIds;

        ResultSet resultSetArtifacts = getResultSet(artifactsQuery);

        artifactsList = createArtifactsList(resultSetArtifacts);

        System.out.println(artifactsQuery);
        System.out.println(artifactsList.size());
        return artifactsList;
    } //korzystnaie

    private String createWhereWithPossessedArtifacts(ResultSet resultSetArtifactsPossessed){
        ResultSetMetaData resultSetMetaData;
        String whereClauseIds = "WHERE";
        try{

            resultSetMetaData = resultSetArtifactsPossessed.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            while(resultSetArtifactsPossessed.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if(i == columnsNumber){
                        whereClauseIds += " artifact_id = " + resultSetArtifactsPossessed.getInt(i) + ";";
                    }else{
                        whereClauseIds += " artifact_id = " + resultSetArtifactsPossessed.getInt(i) + " OR";
                    }

                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return whereClauseIds;
    }

    private List<Artifact> createArtifactsList(ResultSet resultSetArtifacts){
        List<Artifact> artifactsList = new ArrayList<>();
        try{
            while(resultSetArtifacts.next()){
                int id = resultSetArtifacts.getInt(1);
                String name = resultSetArtifacts.getString(2);
                String description = resultSetArtifacts.getString(3);
                int price = resultSetArtifacts.getInt(4);
                artifactsList.add(new Artifact(id,name ,description, price));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return artifactsList;
    }


    @Override
    public int getPriceOfArtifact(int artifactId) {
        String artifactsTableQuery = "SELECT price FROM Artifacts WHERE artifact_id = " + artifactId + ";";
        ResultSet resultSetArtifacts = getResultSet(artifactsTableQuery);
        int price = 0;
        try{
            while (resultSetArtifacts.next()) {
                price = resultSetArtifacts.getInt(1);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return price;

    } //musthave

    @Override
    public void addNewPossesion(int codecoolerId, int artifactId) { //musthave
        String addPossesionQuery = String.format("INSERT INTO artifacts_in_possess (artifact_id, codecooler_id) VALUES (%d, %d)", artifactId, codecoolerId );
        try{
            statement.executeUpdate(addPossesionQuery);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Integer> readTeamMembersId(int codecooler_id) {
        List<Integer> teamMembersId = new ArrayList<>();
        String teamNameQuery = "SELECT team_name FROM teams WHERE codecooler_id = " + codecooler_id + ";";
        ResultSet resultSetTeamName = getResultSet(teamNameQuery);
        String teamName = "";
        try{
            while (resultSetTeamName.next()) {
                teamName = resultSetTeamName.getString("team_name");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        String codecoolersIdQuery = "SELECT codecooler_id FROM teams WHERE team_name = '" + teamName + "';";
        ResultSet resultSetCodecoolersId = getResultSet(codecoolersIdQuery);
        ResultSetMetaData resultSetMetaData;
        try{

            resultSetMetaData = resultSetCodecoolersId.getMetaData();
            int columnsNumber = resultSetMetaData.getColumnCount();
            while(resultSetCodecoolersId.next()){
                for(int i = 1; i <= columnsNumber; i++){
                   teamMembersId.add(resultSetCodecoolersId.getInt(1));
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }

        return teamMembersId;
    } //musthave

    @Override
    public void subtractCodecoolersCoolcoins(int codecoolerId, int artifactPrice) {
        int coins = readCoins(codecoolerId) - artifactPrice;
        String updateCoinsQuery = String.format("UPDATE codecoolers SET coolcoins = %d WHERE codecooler_id = %d;", coins, codecoolerId);
        try{
            statement.executeUpdate(updateCoinsQuery);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }


    }

    public void editCodecoolerTeam(int id, String teamName){
        String editTeamQuery = "UPDATE teams SET team_name = '" + teamName + "' WHERE codecooler_id = " + id + ";";
        try{
            statement.executeUpdate(editTeamQuery);
            connection.commit();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void createNewTeam(int id, String teamName){
        String createTeamQuery = "INSERT INTO teams (team_name, codecooler_id) VALUES (" + "\'" + teamName + "\'," + id + ");";
        try{
            deleteTeam(id, "teams");
            statement.executeUpdate(createTeamQuery);
            connection.commit();
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

    private void getStatement(){
        try{
            statement = connection.createStatement();
        }catch(SQLException e){
            e.printStackTrace();
        }

    }

    private void deleteTeam(int id, String table) throws SQLException{
        String deleteFromTeamQuery = "DELETE FROM " + table + " WHERE codecooler_id = " + id + ";";
        try{
            statement.executeUpdate(deleteFromTeamQuery);
        }catch(NullPointerException e){
            System.out.println("Nothing to delete!");
        }

    }
}
