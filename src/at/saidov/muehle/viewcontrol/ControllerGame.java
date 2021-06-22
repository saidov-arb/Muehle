package at.saidov.muehle.viewcontrol;

import at.saidov.muehle.model.Board;
import at.saidov.muehle.model.Game;
import at.saidov.muehle.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerGame implements Initializable
{
    @FXML GridPane gp_field;
    @FXML Label lbl_status,lbl_playerone,lbl_playertwo;
    Button[] btn_gamebuttons;

    Game zisGame;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Spiel, 2 Spieler und Board erstellen.
        zisGame = new Game();
        zisGame.getPlayers()[0] = new Player();
        zisGame.getPlayers()[1] = new Player();
        zisGame.setBoard(new Board());

        //Den Status auf Setzen geben, die Anzahl der zu platzierenden Steine und einen Indikator angeben.
        lbl_status.setText("Setzen");
        lbl_playerone.setText("➤ "+lbl_playerone.getText()+zisGame.getPlayers()[0].getStones());
        lbl_playertwo.setText(lbl_playertwo.getText()+zisGame.getPlayers()[1].getStones());

        //Größe 24, weil es nur 24 Felder zum anklicken gibt...
        btn_gamebuttons = new Button[24];


        //Designtechnische Größe der Labels auf Maximal mögliche Größe geben.
        //Funktion clickOnFieldToPlace hergeben.
        for (int i = 0; i < btn_gamebuttons.length; i++)
        {
            btn_gamebuttons[i] = new Button();
            btn_gamebuttons[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn_gamebuttons[i].setFont(new Font("Consolas", 36));
            btn_gamebuttons[i].setText("O");
            btn_gamebuttons[i].setOnAction(this::clickOnFieldToPlace);
            btn_gamebuttons[i].setBorder(null);
            btn_gamebuttons[i].setBackground(null);
        }


        //Allen Labels Ids vergeben.
        //Hier hat mein Gehirn komplett durchgeschwitzt. Es ist genial.
        //Die Buttons erhalten als Id einfach die Koordinaten.
        int btncounter = 0;
        for (int i = 0; i < Board.FIELDSIZE; i++)
        {
            for (int j = 0; j < Board.FIELDSIZE; j++)
            {
                if (zisGame.getBoard().getField()[i][j] == 0)
                {
                    btn_gamebuttons[btncounter].setId(j+"|"+i);
                    gp_field.add(btn_gamebuttons[btncounter],j,i);
                    btncounter++;
                }
            }
        }

        for (int i = 0; i < btn_gamebuttons.length; i++)
        {
            System.out.println(btn_gamebuttons[i].getId());
        }
    }


    //Wenn man auf den Button clickt, wird der Stein platziert und die zu platzierenden Steine werden
    //um 1 vermindert.
    //Bei neu geschlossener Mühle wird zu clickOnFieldToTake gewechselt.
    //Bei nicht mehr genug Steine zum legen da sind, wird zu clickOnFieldToShowMoveableFields gewechselt.
    //Bei nicht verfügbarem Feld (besetzt) wird einfach ignoriert.
    @FXML
    public void clickOnFieldToPlace(ActionEvent av)
    {
        Button iButton = (Button) av.getSource();

        //Die Koordinaten auslesen anhand der ID.
        int x = Integer.parseInt(iButton.getId().substring(0,1));
        int y = Integer.parseInt(iButton.getId().substring(2));


        zisGame.placeStone(x,y);

        updateButtons();
        updateStatusbar();
    }


    //Wenn man auf den Button clickt, wird der Stein genommen und die Punkte werden um 1 erhöht.
    //Dann wieder zurück zu clickOnFieldToPlace, oder clickOnFieldToShowMoveableFields.
    //Bei geschlossenen Mühlen wird ignoriert.
    //Bei eigenen Steinen wird ignoriert.
    //Bei nur mehr 2 Steinen übrig wird Gewinner ausfindig gemacht.
    @FXML
    public void clickOnFieldToTake(ActionEvent av)
    {
        Button iButton = (Button) av.getSource();

        //Die Koordinaten auslesen anhand der ID.
        int x = Integer.parseInt(iButton.getId().substring(0,1));
        int y = Integer.parseInt(iButton.getId().substring(2));


    }


    //Wenn man auf den Button clickt, werden die Felder markiert, in denen man den Stein bewegen kann.
    @FXML
    public void clickOnFieldToShowMoveableFields(ActionEvent av)
    {
        Button iButton = (Button) av.getSource();

        //Die Koordinaten auslesen anhand der ID.
        int x = Integer.parseInt(iButton.getId().substring(0,1));
        int y = Integer.parseInt(iButton.getId().substring(2));


    }




    //Aktualisiert den Inhalt der Buttons.
    public void updateButtons()
    {
        int btncounter = 0;
        for (int i = 0; i < Board.FIELDSIZE; i++)
        {
            for (int j = 0; j < Board.FIELDSIZE; j++)
            {
                if (zisGame.getBoard().getField()[i][j] != 7)
                {
                    btn_gamebuttons[btncounter].setText(String.valueOf(zisGame.getBoard().getField()[i][j]));
                    btncounter++;
                }
            }
        }
    }

    //Aktualisiert die Statusbar unten. (Die Hbox, wo PlayerOne, PlayerTwo und Setzen/Bewegen drinnensteht.)
    public void updateStatusbar()
    {
        
    }






    //Soll zum Codesparen dienen. Und zur verbesserten Übersicht.
    //Parameter können sein:
    //place     ->  clickOnFieldToPlace()
    //take      ->  clickOnFieldToTake()
    //(move)    ->  clickOnFieldToShowMoveableFields()
    //null      ->  null
    public void buttonActionChanger(String action)
    {
        for (Button btn_gamebutton : btn_gamebuttons)
        {
            switch (action)
            {
                case "place":
                    btn_gamebutton.setOnAction(this::clickOnFieldToPlace);
                    break;
                case "take":
                    btn_gamebutton.setOnAction(this::clickOnFieldToTake);
                    break;
                case "null":
                    btn_gamebutton.setOnAction(null);
                    break;
            }
        }
    }
}
