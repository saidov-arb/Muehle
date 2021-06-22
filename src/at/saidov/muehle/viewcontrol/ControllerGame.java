package at.saidov.muehle.viewcontrol;

import at.saidov.muehle.model.Board;
import at.saidov.muehle.model.Game;
import at.saidov.muehle.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame implements Initializable
{
    @FXML GridPane gp_field;
    @FXML Label lbl_status,lbl_playerone,lbl_playertwo;

    Game zisGame;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        zisGame = new Game();
        zisGame.getPlayers()[0] = new Player();
        zisGame.getPlayers()[1] = new Player();
        zisGame.setBoard(new Board());

        lbl_status.setText("Setzen");
        lbl_playerone.setText(lbl_playerone.getText()+zisGame.getPlayers()[0].getStones());
        lbl_playertwo.setText(lbl_playertwo.getText()+zisGame.getPlayers()[1].getStones());
    }
}
