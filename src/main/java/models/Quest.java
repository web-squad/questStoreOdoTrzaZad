package models;

public class Quest extends Model{

    private int id;
    private String name;
    private String description;
    private int reward;

    public Quest(int id, String name, String description, int reward) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public Quest(String name, String description, int reward) {
        this.name = name;
        this.description = description;
        this.reward = reward;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    @Override
    public String toString() {
        return "Quest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", reward=" + reward +
                '}';
    }
}
