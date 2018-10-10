package models;

import java.util.Map;

public class Level {
    String levelId;
    String threshold;
    String name;
    Map<String, String > collectedData;

    public Level(Map<String, String> collectedData){
        name = collectedData.get("levelName");
        threshold = collectedData.get("threshold");
        this.collectedData = collectedData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getId() {
        return levelId;
    }

    public void setId(String id) {
        this.levelId = id;
    }
}
