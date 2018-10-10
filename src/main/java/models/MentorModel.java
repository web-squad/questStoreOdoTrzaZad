package models;

import java.util.List;
import java.util.Map;

public class MentorModel extends User {
    String email;
    String password;
    String name;
    String surname;
    String room;
    Map<String, String> collectedData;

    public MentorModel(Map<String, String> mentorData){
        email = mentorData.get("email");
        password =mentorData.get("password");
        name = mentorData.get("name");
        surname = mentorData.get("surname");
        room = mentorData.get("room");
        collectedData = mentorData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

}
