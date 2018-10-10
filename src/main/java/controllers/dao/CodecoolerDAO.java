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


    } //musthave

    @Override
    public void addNewPossesion(int codecoolerId, int artefactId) { //musthave


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
}
