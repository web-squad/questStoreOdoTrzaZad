package controllers;

import models.CodecoolerModel;

public class CodecoolerController {
    private CodecoolerModel codecoolerModel;
    private CodecoolerControllerDAO codecoolerControllerDAO;

    public CodecoolerController(String email, CodecoolerControllerDAO codecoolerController){
        this.codecoolerControllerDAO = codecoolerController;
        this.codecoolerModel = initiateCodecoolerModel(email);

    }



    public void run(){

    }


    public void showWallet(){
        System.out.println(codecoolerControllerDAO.readEmailsCoolcoinsAndArtefacts(email));
    }


    public void buyItem(String artefactID){
        int artefactPrice = codecoolerControllerDAO.getPriceOfArtefact(artefactID);
        int codecoolersCoins = codecoolerModel.getCoolcoins();
        String email = codecoolerModel.getEmail();
        if(codecoolersCoins >= artefactPrice){
            codecoolersCoins -= artefactPrice;
            codecoolerModel.setCodecoolerCoins(codecoolersCoins);
            codecoolerControllerDAO.addNewPossesion(email, artefactID);
            System.out.println("You bought that item!");
        }else{
            System.out.println("You don't have enough coolcoins to buy this!");
        }

    }


    public void teamBuyItem(){

    }


    public void changeTeam(String teamName){
        String email = codecoolerModel.getEmail();
        codecoolerControllerDAO.editCodecoolerTeam(email, teamName);
        System.out.println("You moved to team " + teamName);
    }

    public void createNewTeam(String newTeamName){
        codecoolerControllerDAO.createNewTeam(newTeamName);
        System.out.println("You created new team!");
    }


    private CodecoolerModel initiateCodecoolerModel(String email){
        int coolcoins = codecoolerControllerDAO.readCoins(email);
        int expLevel = codecoolerControllerDAO.checkCoinsEverOwned(email); //robocza nazwa
        String questInProgress = codecoolerControllerDAO.checkQuestInProgress(email); //robocza nazwa
        String room = codecoolerControllerDAO.readCodecoolerClass(email);
        String teamID = codecoolerControllerDAO.readTeamName(email);
        String nickName = codecoolerControllerDAO.getNickName(email);
        String first_name = codecoolerControllerDAO.getFirstName(email);
        String second_name = codecoolerControllerDAO.getSecondName(email);
        return new CodecoolerModel(nickName, first_name, second_name, email, room, teamID, expLevel, coolcoins, questInProgress);
    }
}
