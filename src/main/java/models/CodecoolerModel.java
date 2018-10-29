package models;

public class CodecoolerModel extends User {

    private int id;
    private String email;
    private String password;
    private String nickname;
    private int accessLevel = 1;
    private int coolcoins;
    private int expLevel = 1;
    private int room = 1;
    private int coolcoinsEverEarned;
    private int questInProgress = 1;
    private String firstName;
    private String lastName;
    private int teamID = 1;


    public CodecoolerModel(String firstName, String lastName, String email, String nickname, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public CodecoolerModel(int id, String firstName, String lastName, String email, String nickname, String password, int accessLevel, int coolcoins,
                           int expLevel, int room, int coolcoinsEverEarned, int questInProgress, int teamID) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.accessLevel = accessLevel;
        this.coolcoins = coolcoins;
        this.expLevel = expLevel;
        this.room = room;
        this.coolcoinsEverEarned = coolcoinsEverEarned;
        this.questInProgress = questInProgress;
        this.teamID = teamID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }

    public int getCoolcoins() {
        return coolcoins;
    }

    public void setCoolcoins(int coolcoins) {
        this.coolcoins = coolcoins;
    }

    public int getExpLevel() {
        return expLevel;
    }

    public void setExpLevel(int expLevel) {
        this.expLevel = expLevel;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getCoolcoinsEverEarned() {
        return coolcoinsEverEarned;
    }

    public void setCoolcoinsEverEarned(int coolcoinsEverEarned) {
        this.coolcoinsEverEarned = coolcoinsEverEarned;
    }

    public int getQuestInProgress() {
        return questInProgress;
    }

    public void setQuestInProgress(int questInProgress) {
        this.questInProgress = questInProgress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    @Override
    public String toString() {
        return "CodecoolerModel{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", coolcoins=" + coolcoins +
                ", expLevel=" + expLevel +
                '}';
    }
}
