package models;

import java.util.Map;

public class MentorModel extends User {

    private String  id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private String room;
    private String nickName;
    private Map<String, String> collectedData;

    public MentorModel(Map<String, String> mentorData){
        email = mentorData.get("email");
        password =mentorData.get("password");
        name = mentorData.get("firstName");
        surname = mentorData.get("surname");
        room = mentorData.get("room");
        nickName = mentorData.get("nickName");
        collectedData = mentorData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        collectedData.put("id", id);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getRoom() {
        return room;
    }

    public String getNickName(){
        return nickName;
    }

}
