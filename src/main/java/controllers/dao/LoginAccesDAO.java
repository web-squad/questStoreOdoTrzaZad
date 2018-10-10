package controllers.dao;

import java.sql.Connection;

public class LoginAccesDAO implements LoginAccesDAOInterface {
    Connection connection;

    public LoginAccesDAO(Connection connection){
        this.connection = connection;
    }

    @Override
    public int readLoginData(String email, String pass) {
        return 0;
    }
}
