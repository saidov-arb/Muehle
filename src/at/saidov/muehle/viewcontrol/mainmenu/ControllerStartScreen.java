package at.saidov.muehle.viewcontrol.mainmenu;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerStartScreen
{
    /**♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦
     * Autor: Arbi Saidov
     * Datum: 21.06.2021
     * Beschreibung: Controller Klasse für StartScreen.
     *          Möge Gott mir beistehen.
     ♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦*/


    @FXML
    Label lbl_menucontinue,lbl_menunewgame,lbl_menuscoreboard,lbl_menurules,lbl_menusettings,lbl_menuexit;



    @FXML
    public void continueGame(){
        System.out.println("Continue.");
    }

    @FXML
    public void startNewGame(){
        System.out.println("New Game.");
    }

    @FXML
    public void showScoreboard(){
        System.out.println("Scoreboard.");
    }

    @FXML
    public void showRules(){
        System.out.println("Rules.");
    }

    @FXML
    public void showSettings(){
        System.out.println("Settings.");
    }

    @FXML
    public void exitGame(){
        System.out.println("Exit Game.");
        Platform.exit();
    }
}
