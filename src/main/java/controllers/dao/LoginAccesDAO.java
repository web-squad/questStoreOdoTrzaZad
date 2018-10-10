package controllers.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class LoginAccesDAO implements LoginAccesDAOInterface {

    Connection connection;
    List<Integer> loginData;

    public LoginAccesDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public List<Integer> readLoginData(String email, String pass) {
        try{
            retriveData(email, pass);
            return loginData;
        }catch (SQLException e){
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
        return null;
    }

    private void retriveData(String email, String pass) throws SQLException {
        Statement stmt = connection.createStatement();
        String sql = String.format("SELECT id, access_level FROM LoginAccess"
                + "\n VALUES ('%s', '%s');", email, pass);
        stmt.executeUpdate(sql);
        ResultSet rs = stmt.executeQuery( sql);
        while ( rs.next() ) {
            loginData.set(0, rs.getInt("access_level"));
            loginData.set(1, rs.getInt("id"));
        }
        rs.close();
        stmt.close();
    }
}
