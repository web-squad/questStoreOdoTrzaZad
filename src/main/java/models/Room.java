package models;

import java.util.Map;

public class Room {
    String roomId;
    String roomName;
    String roomDescription;
    Map<String, String > collectedData;

    public Room(Map<String, String> collectedData){
      roomName = collectedData.get("roomName");
      roomDescription = collectedData.get("roomDescription");
      this.collectedData = collectedData;
    }

    public Map<String, String> getCollectedData() {
        return collectedData;
    }

    public String getId() {
        return roomId;
    }

    public void setId(String id) {
        this.roomId = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomDescription() {
        return roomDescription;
    }
}
