package controllers.dao;

import java.util.List;

public interface LoginAccesDAOInterface {
    List<Integer> readLoginData(String email, String pass);
}
