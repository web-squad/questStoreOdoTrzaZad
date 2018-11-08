package controllers.dao;

import java.util.ArrayList;
import java.util.List;

import models.Artifact;


public interface CodecoolerDAOInterface {

    int readCoins(int codecoolerId);
    int checkCoinsEverOwned(int codecoolerId);
    int checkQuestInProgress(int codecoolerId);
    int readCodecoolerClass(int codecoolerId);
    String readTeamName(int codecoolerId);
    String getNickName(int codecoolerId);
    String getFirstName(int codecoolerId);
    String getSecondName(int codecoolerId);
    String getEmail(int codecoolerId);
    List<String> readArtifacts();
    List<Artifact> readCodecoolersArtifacts(int codecoolerId);
    int getPriceOfArtifact(int artifactId);
    void addNewPossesion(int codecoolerId, int artifactId);
    List<Integer> readTeamMembersId(int artifactId);
    void subtractCodecoolersCoolcoins(int codecoolerId, int artifactPrice);
    void editCodecoolerTeam(int codecoolerId, String teamName);
    void createNewTeam(int codecoolerId, String newTeam);


}
