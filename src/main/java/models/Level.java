package models;

import java.util.Map;

public class Level extends Model{
    private String id;
    private String threshold;
    private String name;
    private Map<String, String > collectedData;

    public Level(Map<String, String> collectedData){
        name = collectedData.get("levelName");
        threshold = collectedData.get("threshold");
        this.collectedData = collectedData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getLevelName() {
        return name;
    }

    public String getThreshold() {
        return threshold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
