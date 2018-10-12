package models;

import java.util.Map;

public class Room extends Model{
    private String id;
    private String roomName;
    private String roomDescription;
    private Map<String, String > collectedData;

    public Room(Map<String, String> collectedData){
      roomName = collectedData.get("roomName");
      roomDescription = collectedData.get("roomDescription");
      this.collectedData = collectedData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }
}
