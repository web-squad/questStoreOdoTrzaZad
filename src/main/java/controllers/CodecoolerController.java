package controllers;

import models.CodecoolerModel;
import views.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CodecoolerController {
    private CodecoolerModel codecoolerModel;
    private CodecoolerControllerDAO codecoolerControllerDAO;
    private View view;

    public CodecoolerController(int id, CodecoolerControllerDAO codecoolerController){
        this.codecoolerControllerDAO = codecoolerController;
        this.codecoolerModel = initiateCodecoolerModel(id);
        view = new View();

    }


    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        boolean isRunning = true;
        while (isRunning) {
            view.printMenu("(1) Show wallet", "(2) Buy item", "(3) Buy item for your team", "(4) Change team", "(5) Create new Team", "(6) exit");
            try {
                answer = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("Nie mogę nic odebrać!");
            }

            char charAnswer = answer.charAt(0);
            switch (charAnswer) {
                case '1':
                    showWallet();
                    break;

                case '2':
                    String artefactId = view.getInputString("Provide artefact id which you want to buy!");
                    System.out.println(codecoolerControllerDAO.readArtefacts());
                    buyItem(artefactId);
                    break;

                case '3':
                    String teamArtefactId = view.getInputString("Provide artefact id which you want to buy for your team!");
                    teamBuyItem(teamArtefactId);
                    break;
                case '4':
                    String newTeamName = view.getInputString("Please provide name of a team you'd like to join");
                    changeTeam(newTeamName);
                    break;
                case '5':
                    String newlyCreatedTeamName = view.getInputString("Please provide name of a team you want to create!");
                    createNewTeam(newlyCreatedTeamName);
                    break;
                case '6':
                    isRunning = false;
                    break;
            }
        }
    }



    public void showWallet(){
        int id = codecoolerModel.getId();
        System.out.println(codecoolerControllerDAO.readEmailsCoolcoinsAndArtefacts(id));
    }


    public void buyItem(String artefactID){
        int artefactPrice = codecoolerControllerDAO.getPriceOfArtefact(artefactID);
        int codecoolersCoins = codecoolerModel.getCoolcoins();
        int id = codecoolerModel.getId();
        if(codecoolersCoins >= artefactPrice){
            codecoolersCoins -= artefactPrice;
            codecoolerModel.setCodecoolerCoins(codecoolersCoins); //dodaje do modelu dao sie tym pozniej zajmie
            codecoolerControllerDAO.addNewPossesion(id, artefactID);
            System.out.println("You bought that item!");
        }else{
            System.out.println("You don't have enough coolcoins to buy this!");
        }

    }


    public void teamBuyItem(String artefactId){
        int id = codecoolerModel.getId();
        ArrayList<Integer> teamMembersIds = codecoolerControllerDAO.readTeamMembersId(id);
        int costOfAnArtefact = codecoolerControllerDAO.getPriceOfAnArtefact(artefactId);
        if(checkTeamMembersMoney(teamMembersIds, costOfAnArtefact)){
            for(Integer teamMemberId : teamMembersIds){
                codecoolerControllerDAO.subtractCodecoolerCoolcoins(costOfAnArtefact/teamMembersIds.size());
                codecoolerControllerDAO.addNewPossesion(teamMemberId, artefactId);
            }
        }
    }


    public void changeTeam(String teamName){
        int id = codecoolerModel.getId();
        codecoolerControllerDAO.editCodecoolerTeam(id, teamName);
        System.out.println("You moved to team " + teamName);
    }

    public void createNewTeam(String newTeamName){
        codecoolerControllerDAO.createNewTeam(newTeamName);
        System.out.println("You created new team!");
    }


    private CodecoolerModel initiateCodecoolerModel(int id){
        int coolcoins = codecoolerControllerDAO.readCoins(id);
        int expLevel = codecoolerControllerDAO.checkCoinsEverOwned(id); //robocza nazwa
        String questInProgress = codecoolerControllerDAO.checkQuestInProgress(id); //robocza nazwa
        String room = codecoolerControllerDAO.readCodecoolerClass(id);
        String teamID = codecoolerControllerDAO.readTeamName(id);
        String nickName = codecoolerControllerDAO.getNickName(id);
        String first_name = codecoolerControllerDAO.getFirstName(id);
        String second_name = codecoolerControllerDAO.getSecondName(id);
        String email = codecoolerControllerDAO.getEmail(id);
        return new CodecoolerModel(id, nickName, first_name, second_name, email, room, teamID, expLevel, coolcoins, questInProgress);
    }

    private boolean checkTeamMembersMoney(ArrayList<Integer> ids, int costs){
        int costPerPerson = costs/ids.size();
        for(Integer memberId : ids){
            int coins = codecoolerControllerDAO.readCoins(memberId);
            if(coins < costPerPerson){
                return false;
            }
        }
        return true;
    }
}
