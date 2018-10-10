package controllers;

import controllers.dao.MentorDAO;
import models.*;
import views.View;

import java.util.ArrayList;
import java.util.List;

public class MentorController extends UserController{

    MentorModel mentor;
    View view;
    MentorDAO mentorDAO;

    public MentorController(int id, MentorDAO mentorDAO) {
        this.mentorDAO = mentorDAO;
        this.mentor = mentorDAO.createMentor(id);
        view = new View();
    }

    public void startUserSession(){
        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Log out",
                    "Manage Codecoolers",
                    "Add artifact to store",
                    "Edit artifact in store",
                    "Add new quest",
                    "Edit existing quest");

            option = view.getInputInt(0, 5);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    manageCodecoolers();
                    break;
                case 2:
                    addArtifactToStore();
                    break;
                case 3:
                    editArtifactInStore();
                    break;
                case 4:
                    addNewQuest();
                case 5:
                    editExistingQuest();
            }
        }
    }


    public void manageCodecoolers() {
        boolean isRunning = true;
        int option;

        while (isRunning) {

            view.printMenu("Exit",
                    "List all Mentors",
                    "Add new codecooler",
                    "Edit existing codecooler",
                    "Remove codecooler",
                    "Mark item as used",
                    "Add artifact to store",
                    "Mark quest as completed",
                    "Check codecooler wallet");

            option = view.getInputInt(0, 5);

            switch (option) {

                case 0:
                    isRunning = false;
                    break;
                case 1:
                    listAllCodecoolers();
                    break;
                case 2:
                    addCodecooler();
                    break;
                case 3:
                    editCodecooler();
                    break;
                case 4:
                    removeCodecooler();
                    break;
                case 5:
                    markItemAsUsed();
                    break;
                case 6:
                    markQuestAsCompleted();
                    break;
                case 7:
                    checkCodecoolerWallet();
                    break;
            }
        }
    }


    public void listAllCodecoolers() {
        List<CodecoolerModel> codecoolers = new ArrayList<>();
        codecoolers = mentorDAO.allCodecoolers();

        for (int i = 0; i < codecoolers.size(); i++) {
            view.print(codecoolers.get(i).toString());
        }
    }


    public void addCodecooler() {
        CodecoolerModel cm = createCodecooler();
        MentorDAO.createCodecooler(cm);
    }


    public void editCodecooler() {
        CodecoolerModel cm = createCodecooler();
        mentorDAO.editCodecooler(codecoolerID(), cm);
    }


    public void removeCodecooler() {
        mentorDAO.removeCodecooler(codecoolerID());
    }


    public void markItemAsUsed() {
        int codecooler_id = codecoolerID();
        printCodecoolerArtifact(codecooler_id);

        int artifact_id = view.getInputInt(0, artifacts.size());
        mentorDAO.markItemAsUsed(codecooler_id, artifact_id);
    }


    public void markQuestAsCompleted() {
        mentorDAO.markQuestAsCompleted(codecoolerID());
    }


    public void checkCodecoolerWallet() {
        int codecooler_id = codecoolerID();
        view.print(mentorDAO.codecoolerCoins(codecooler_id));
        printCodecoolerArtifact(codecooler_id);
    }


    public void addArtifactToStore() {
        mentorDAO.addArtifactToStore(newArtifact());
    }


    public void editArtifactInStore() {
        List<Model> artifacts = mentorDAO.listOfArtifactsInShop();

        for (int i = 0; i < artifacts.size(); i++) {
            view.print(artifacts.get(i).toString());
        }

        view.print("Which artifact you like to edit? (id)");
        int artifact_id = view.getInputInt();
        Artifact editedArtifact = newArtifact();
        mentorDAO.editArtifact(artifact_id, editedArtifact);
    }


    public void addNewQuest() {
        mentorDAO.addNewQuest(newQuest());
    }


    public void editExistingQuest() {
        List<Model> quests = mentorDAO.listOfQuests();

        view.printModelList(quests);

        view.print("Which quest you like to edit? (id)");
        int quest_id = view.getInputInt();
        Quest editedQuest = newQuest();
        mentorDAO.editQuest(quest_id, editedQuest);
    }


    private CodecoolerModel createCodecooler() {
        String firstName = view.getInputString("First name");
        String lastName = view.getInputString("Last name");
        String email = view.getInputString("email");
        String nickname = view.getInputString("nickname");
        String password = view.getInputString("password");

        CodecoolerModel cm = new CodecoolerModel(firstName, lastName, email, nickname, password);
        return cm;
    }

    private int codecoolerID() {
        view.print("codecooler ID? ");
        int id = view.getInputInt();
        return id;
    }


    private void printCodecoolerArtifact(int codecooler_id) {
        List<Model> artifacts = mentorDAO.codecoolerArtifacts(codecooler_id);

        for (int i = 0; i < artifacts.size(); i++) {
            view.print(artifacts.get(i).toString());
        }
    }


    private Artifact newArtifact() {
        String itemName = view.getInputString("Item name");
        String itemDescription = view.getInputString("Item Description");
        view.print("Item price ");
        int itemPrice = view.getInputInt();
        Artifact artifact = new Artifact(itemName, itemDescription, itemPrice);
        return artifact;
    }


    private Quest newQuest() {
        String questName = view.getInputString("Quest name");
        String questDescription = view.getInputString("Quest description");
        view.print("Quest reward ");
        int questReward = view.getInputInt();
        Quest quest = new Quest(questName, questDescription, questReward);
        return quest;
    }
}
