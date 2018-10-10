package controllers.dao;

import java.util.ArrayList;

public interface CodecoolerDAOInterface {
    int readCoins(int codecoolerId);
    int checkCoinsEverOwned(int id);
    String checkQuestInProgress(int id);
    String readCodecoolerClass(int id);
    String readTeamName(int id);
    String getNickName(int id);
    String getFirstName(int id);
    String getSecondName(int id);
    String getEmail(int id);
    String readArtefacts();
    String readEmailsCoolcoinsAndArtefacts(int codecoolerId);
    int getPriceOfArtefact(int artefactId);
    void addNewPossesion(int codecoolerId, int artefactId);
    ArrayList<Integer> readTeamMembersId(int artefactId);
    void subtractCodecoolersCoolcoins(int codecoolerId);


}
