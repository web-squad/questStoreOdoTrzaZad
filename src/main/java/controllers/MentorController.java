package controllers;

import controllers.dao.MentorDAO;
import models.MentorModel;
import views.View;

public class MentorController extends UserController{

    MentorModel mentor;
    View view;
    MentorDAO mentorDAO;

    public MentorController(int id, MentorDAO mentorDAO) {

    }

    public void startUserSession(){
        System.out.println("działa");
    }
}
