package controllers.dao;


import org.postgresql.util.PSQLException;
import views.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginAccesDAO implements LoginAccesDAOInterface {

    private Connection connection;
    private List<Integer> loginData;

    public LoginAccesDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Integer> readLoginData(String email, String pass) {
        try{
            retriveData(email, pass);
            return loginData;
        }catch (PSQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.out.println("No such user");
            return null;
        }catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    private void retriveData(String email, String pass) throws SQLException {
        loginData = new ArrayList<>();
        Statement stmt = connection.createStatement();
        String sql = String.format("SElECT id, access_level FROM login_access WHERE email = '%s' AND password = '%s' ", email, pass);
        ResultSet rs = stmt.executeQuery(sql);
        while ( rs.next() ) {
            loginData.add(rs.getInt("access_level"));
            loginData.add(rs.getInt("id"));
        }
        rs.close();
        stmt.close();
    }
}
