package controllers.dao;

import models.CodecoolerModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CodecoolerDAO implements CodecoolerDAOInterface {
    private Connection connection;
    private Statement statement;

    CodecoolerDAO(Connection connection) throws SQLException {
        this.connection = connection;
        statement = connection.createStatement();
    }

    @Override
    public int readCoins(int codecoolerId) {
        String query = "SELECT coolcoins FROM codecoolers WHERE id = " + codecoolerId + ";";
        ResultSet resultSet = null;
        int coins = 0;
        try{
            resultSet = statement.executeQuery(query);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        try{
            coins = resultSet.getInt(1);
        }catch(SQLException e){
            e.printStackTrace();
        }

        return coins;
    }

    public CodecoolerModel getCodecoolerModel(){
        String query = "SELECT coolcoins FROM codecoolers WHERE id = " + codecoolerId + ";";
        ResultSet resultSet = null;
        int coins = 0;
        try{
            resultSet = statement.executeQuery(query);
        }catch(SQLException e){
            System.out.println("Couldn't find selected query");
        }
        try{
            int coolcoins = 
            int expLevel = //robocza nazwa
            String questInProgress = //robocza nazwa
            String room =
            String teamID =
            String nickName =
            String first_name =
            String second_name =
            String email =
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public int checkCoinsEverOwned(int id) {
        return 0;
    }

    @Override
    public String checkQuestInProgress(int id) {
        return null;
    }

    @Override
    public String readCodecoolerClass(int id) {
        return null;
    }

    @Override
    public String readTeamName(int id) {
        return null;
    }

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
    }

    @Override
    public String readArtefacts() {
        return null;
    }

    @Override
    public String readEmailsCoolcoinsAndArtefacts(int codecoolerId) {
        return null;
    }

    @Override
    public int getPriceOfArtefact(int artefactId) {
        return 0;
    }

    @Override
    public void addNewPossesion(int codecoolerId, int artefactId) {

    }

    @Override
    public ArrayList<Integer> readTeamMembersId(int artefactId) {
        return null;
    }

    @Override
    public void subtractCodecoolersCoolcoins(int codecoolerId) {

    }
}
