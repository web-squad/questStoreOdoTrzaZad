package controllers;

import controllers.dao.CodecoolerDAO;
import models.CodecoolerModel;
import views.View;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerController extends UserController{
    private CodecoolerModel codecoolerModel;
    private CodecoolerDAO codecoolerControllerDAO;
    private View view;

    public CodecoolerController(int id, CodecoolerDAO codecoolerController){
        this.codecoolerControllerDAO = codecoolerController;
        this.codecoolerModel =  codecoolerControllerDAO.getCodecoolerModel(id);
        view = new View();

    }


    public void startUserSession() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String answer = "";
        boolean isRunning = true;
        while (isRunning) {
            view.printMenu("Quit", "Show Wallet", "Buy item", "Buy item for your team", "Change team", "Create new Team");
            try {
                answer = bufferedReader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            char charAnswer = answer.charAt(0);
            switch (charAnswer) {
                case '1':
                    showWallet();
                    break;
                case '2':
                    System.out.println("Provide artefact id which you want to buy!");
                    int artefactId = view.getInputInt();
                    System.out.println(codecoolerControllerDAO.readArtifacts());
                    buyItem(artefactId);
                    break;

                case '3':
                    System.out.println("Provide artefact id which you want to buy for your team!");
                    int teamArtefactId = view.getInputInt();
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
                case '0':
                    isRunning = false;
                    break;
            }
        }
    }



    public void showWallet(){
        int id = codecoolerModel.getId();
        System.out.println(codecoolerControllerDAO.readCodecoolersArtifacts(id));
    }


    public void buyItem(int artefactID){
        int artefactPrice = codecoolerControllerDAO.getPriceOfArtifact(artefactID);

        int id = codecoolerModel.getId();
        int codecoolersCoins = codecoolerControllerDAO.readCoins(id);
        if(codecoolersCoins >= artefactPrice){
            codecoolerControllerDAO.subtractCodecoolersCoolcoins(id, artefactPrice); //dodaje do modelu dao sie tym pozniej zajmie
            codecoolerControllerDAO.addNewPossesion(id, artefactID);
            System.out.println("You bought that item!");
        }else{
            System.out.println("You don't have enough coolcoins to buy this!");
        }

    }


    public void teamBuyItem(int artefactId){
        int id = codecoolerModel.getId();
        List<Integer> teamMembersIds = codecoolerControllerDAO.readTeamMembersId(id);
        int costOfAnArtefact = codecoolerControllerDAO.getPriceOfArtifact(artefactId);
        if(checkTeamMembersMoney(teamMembersIds, costOfAnArtefact)){
            for(Integer teamMemberId : teamMembersIds){
                codecoolerControllerDAO.subtractCodecoolersCoolcoins(id, costOfAnArtefact/teamMembersIds.size());
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
        int id = codecoolerModel.getId();
        codecoolerControllerDAO.createNewTeam(id, newTeamName);
        System.out.println("You created new team!");
    }



    private boolean checkTeamMembersMoney(List<Integer> ids, int costs){
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
