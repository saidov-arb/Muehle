package at.saidov.muehle.viewcontrol;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerStartScreen
{
    @FXML
    Label lbl_menucontinue,lbl_menunewgame,lbl_menuscoreboard,lbl_menurules,lbl_menusettings,lbl_menuexit;


    @FXML
    public void clickDings(){
        System.out.println("Dings.");
    }
}
