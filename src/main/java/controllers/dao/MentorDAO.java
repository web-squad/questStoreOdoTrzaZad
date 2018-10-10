package controllers.dao;

import models.Artifact;
import models.CodecoolerModel;
import models.Model;
import models.Quest;

import java.util.List;

public class MentorDAO implements MentorDAOInterface {

    public static void createCodecooler(CodecoolerModel cm) {
    }

    public void editQuest(int quest_id, Quest editedQuest) {
    }

    public List<Model> listOfQuests() {
    }

    public void addNewQuest(Quest newQuest) {
    }

    public List<Model> listOfArtifactsInShop() {
    }

    public void editArtifact(int artifact_id, Artifact editedArtifact) {
    }

    public void addArtifactToStore(Artifact newArtifact) {
    }

    public String codecoolerCoins(int codecooler_id) {
    }

    public void markQuestAsCompleted(int codecoolerID) {
    }

    public void markItemAsUsed(int codecooler_id, int artifact_id) {
    }

    public void removeCodecooler(int codecoolerID) {
    }

    public void editCodecooler(int codecoolerID, CodecoolerModel cm) {
    }

    public List<CodecoolerModel> allCodecoolers() {
    }

    public List<Model> codecoolerArtifacts(int codecooler_id) {
        return null;
    }
}
