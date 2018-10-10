package models;

import java.util.List;
import java.util.Map;

public class MentorModel extends User {
    String email;
    String password;
    String name;
    String surname;
    String room;
    int accessLevel;
    Map<String, String> collectedData;

    public MentorModel(Map<String, String> mentorData){
        accessLevel = 1;
        email = mentorData.get("email");
        password =mentorData.get("password");
        name = mentorData.get("name");
        surname = mentorData.get("surname");
        room = mentorData.get("room");
        collectedData = mentorData;
        collectedData.put("accessLevel", String.valueOf(accessLevel));
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

}
