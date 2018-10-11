package controllers.dao;

import java.util.ArrayList;
import java.util.List;

import models.Artifact;


public interface CodecoolerDAOInterface {

    int readCoins(int codecoolerId);
    int checkCoinsEverOwned(int id);
    int checkQuestInProgress(int id);
    int readCodecoolerClass(int id);
    String readTeamName(int id);
    String getNickName(int id);
    String getFirstName(int id);
    String getSecondName(int id);
    String getEmail(int id);
    String readArtifacts();
    List<Artifact> readCodecoolersArtifacts(int codecoolerId);
    int getPriceOfArtifact(int artifactId);
    void addNewPossesion(int codecoolerId, int artifactId);
    ArrayList<Integer> readTeamMembersId(int artifactId);
    void subtractCodecoolersCoolcoins(int codecoolerId, int artifactPrice);


}
