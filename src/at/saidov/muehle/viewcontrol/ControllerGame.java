package at.saidov.muehle.viewcontrol;

import at.saidov.muehle.model.*;
import javafx.css.Size;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerGame implements Initializable
{
    @FXML GridPane gp_field;
    @FXML Label lbl_status,lbl_playerone,lbl_playertwo;
    Button[] btn_gamebuttons;

    Button buttonToMove;
    ArrayList<int[]> possibilitiesForButtonToMove;


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
        lbl_status.setText("Place.");
        lbl_playerone.setText("➤ "+lbl_playerone.getText()+zisGame.getPlayers()[0].getStones());
        lbl_playertwo.setText(lbl_playertwo.getText()+zisGame.getPlayers()[1].getStones());

        //Gridpane das Hintergrundbild geben.

        //Größe 24, weil es nur 24 Felder zum anklicken gibt...
        btn_gamebuttons = new Button[24];


        //Designtechnische Größe der Labels auf Maximal mögliche Größe geben.
        //Funktion clickOnFieldToPlace hergeben.
        for (int i = 0; i < btn_gamebuttons.length; i++)
        {
            btn_gamebuttons[i] = new Button();
            btn_gamebuttons[i].setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            btn_gamebuttons[i].setFont(new Font("Consolas", 36));
            btn_gamebuttons[i].setText("");
            btn_gamebuttons[i].setOnAction(this::clickOnFieldToPlace);
            btn_gamebuttons[i].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
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

        for (Button btn_gamebutton : btn_gamebuttons)
        {
            System.out.println(btn_gamebutton.getId());
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


        if (zisGame.getPlayers()[0].getStones() > 0 || zisGame.getPlayers()[1].getStones() > 0)
        {
            if (zisGame.getBoard().checkForSpace(x, y))
            {
                zisGame.placeStone(x, y);


                //Wenn gerade eine Mühle geschlossen wurde, soll der Spieler die Rechte kriegen, einen Stein vom
                //Gegner einzusammeln.
                if (zisGame.checkIfMillClosed(x, y) && zisGame.checkIfSingleStoneAvailable())
                {
                    changeButtonAction("take");
                    lbl_status.setText("Take.");
                } else if (zisGame.getPlayers()[0].getStones() < 1 && zisGame.getPlayers()[1].getStones() < 1)
                {
                    zisGame.updatePlayerCounter();
                    lbl_status.setText("Move");
                }
                else
                {
                    zisGame.updatePlayerCounter();
                }
            }
        } else
        {
            changeButtonAction("move");
        }


        //Aktualisiert werden soll immer.
        updateButtons();
        updateStatusbar();
    }


    //Wenn man auf den Button clickt, wird der Stein genommen und die Punkte werden um 1 erhöht.
    //Dann wieder zurück zu clickOnFieldToPlace, oder clickOnFieldToShowMoveableFields.
    //Bei geschlossenen Mühlen wird ignoriert.
    //Bei eigenen Steinen wird ignoriert.
    //Bei nur mehr 2 Steinen übrig wird Gewinner ausfindig gemacht.
    //Wenn keine Steine auf dem Feld lose sind (Steine, die in keiner Mühle drinnen sind, die einfach unterwegs sind),
    //wird die Möglichkeit, Steine aus geschlossenen Mühlen zu nehmen gegeben.
    @FXML
    public void clickOnFieldToTake(ActionEvent av)
    {
        Button iButton = (Button) av.getSource();

        //Die Koordinaten auslesen anhand der ID.
        int x = Integer.parseInt(iButton.getId().substring(0,1));
        int y = Integer.parseInt(iButton.getId().substring(2));


        //Kontrollieren, ob's ee nicht der eigene Stein war, wenn nicht, dann soll der Stein genommen werden.
        //Wenn beide noch nicht alle Steine gelegt haben, wieder zurück zu clickOnFieldToPlace.
        //Wenn alles gesetzt wurde, zurück zu clickOnFieldToShowMoveableFields.
        if (zisGame.getBoard().getField()[y][x] != zisGame.playercounter && zisGame.getBoard().checkForStone(x,y))
        {
            //Wenn Mühle zwar geschlossen ist, aber es sonst keine anderen Steine zum abheben gibt, darf man
            //einfach Steine aus geschlossenen Mühlen nehmen.
            if (!zisGame.checkIfMillClosed(x,y) || !zisGame.checkIfSingleStoneAvailable())
            {
                zisGame.takeStone(x, y);


                if (zisGame.getPlayers()[0].getStones() > 0 || zisGame.getPlayers()[1].getStones() > 0)
                {
                    changeButtonAction("place");
                    lbl_status.setText("Place.");
                } else
                {
                    changeButtonAction("move");
                    lbl_status.setText("Move.");
                }

                zisGame.updatePlayerCounter();
                updateButtons();
                updateStatusbar();
            }
        }
    }


    //Wenn man auf den Button clickt, werden die Felder markiert, in denen man den Stein bewegen kann.
    @FXML
    public void clickOnFieldToShowMoveableFields(ActionEvent av)
    {
        Button iButton = (Button) av.getSource();

        //Die Koordinaten auslesen anhand der ID.
        int x = Integer.parseInt(iButton.getId().substring(0,1));
        int y = Integer.parseInt(iButton.getId().substring(2));



        resetButtonStyle();

        if (zisGame.getBoard().getField()[y][x] == zisGame.playercounter)
        {
            buttonToMove = iButton;
            possibilitiesForButtonToMove = zisGame.getBoard().getPossibilitiesForMovement(x,y);

            //Wenn an der Stelle ein Stein ist und es der eigene Stein ist...
            if (zisGame.getBoard().checkForStone(x, y) && zisGame.getBoard().getField()[y][x] == zisGame.playercounter)
            {
                //Alle Möglichkeiten durchlaufen und die Möglichkeit, die passt, einfach den Hintergrund färben.
                for (int i = 0; i < possibilitiesForButtonToMove.size(); i++)
                {
                    for (Button btn_gamebutton : btn_gamebuttons)
                    {
                        if (btn_gamebutton.getId().substring(0, 1).equals(String.valueOf(possibilitiesForButtonToMove.get(i)[0])) &&
                                btn_gamebutton.getId().substring(2).equals(String.valueOf(possibilitiesForButtonToMove.get(i)[1])))
                        {
                            btn_gamebutton.setBackground(new Background(new BackgroundFill(Color.rgb(100,121,169,0.5), null, null)));
                        }
                    }
                }
            }
        }
        else if (!zisGame.getBoard().checkForStone(x,y) && possibilitiesForButtonToMove != null)
        {
            System.out.println("Bin da. im else if.");

            for (int i = 0; i < possibilitiesForButtonToMove.size(); i++)
            {
                System.out.println("Bin da. im for.");

                if (possibilitiesForButtonToMove.get(i)[0] == x && possibilitiesForButtonToMove.get(i)[1] == y)
                {
                    System.out.println("Bin da. im if.");
                    zisGame.moveStone(Integer.parseInt(buttonToMove.getId().substring(0,1)),Integer.parseInt(buttonToMove.getId().substring(2)),x,y);

                    //Wenn gerade eine Mühle geschlossen wurde, soll der Spieler die Rechte kriegen, einen Stein vom
                    //Gegner einzusammeln.
                    if (zisGame.checkIfMillClosed(x, y) && zisGame.checkIfSingleStoneAvailable())
                    {
                        changeButtonAction("take");
                        lbl_status.setText("Take.");
                    } else
                    {
                        zisGame.updatePlayerCounter();
                    }

                    updateButtons();
                    updateStatusbar();
                }
            }
            buttonToMove = null;
            possibilitiesForButtonToMove = null;
        }
    }




    //Aktualisiert den Inhalt der Buttons.
    public void updateButtons()
    {
        int btncounter = 0;
        for (int i = 0; i < Board.FIELDSIZE; i++) {
            for (int j = 0; j < Board.FIELDSIZE; j++) {
                if (zisGame.getBoard().getField()[i][j] != 7) {
                    if (zisGame.getBoard().getField()[i][j] == 1) {
                        btn_gamebuttons[btncounter].setText("☺");
                    }else if (zisGame.getBoard().getField()[i][j] == 2)
                    {
                        btn_gamebuttons[btncounter].setText("☻");
                    }
                    else{
                        btn_gamebuttons[btncounter].setText("");
                        btn_gamebuttons[btncounter].setBackground(new Background(new BackgroundFill(Color.TRANSPARENT,null,null)));
                    }
                    btncounter++;
                }
            }
        }
    }

    //Aktualisiert die Statusbar unten. (Die Hbox, wo PlayerOne, PlayerTwo und Setzen/Bewegen drinnensteht.)
    public void updateStatusbar()
    {
        if (zisGame.getPlayers()[0].getStones() > 0 || zisGame.getPlayers()[1].getStones() > 0)
        {
            lbl_playerone.setText("Player1: " + zisGame.getPlayers()[0].getStones());
            lbl_playertwo.setText("Player2: " + zisGame.getPlayers()[1].getStones());
        }else{
            lbl_playerone.setText("Player1");
            lbl_playertwo.setText("Player2");
        }

        if (zisGame.playercounter == 1) {
            lbl_playerone.setText("➤ "+lbl_playerone.getText());
        }
        else if (zisGame.playercounter == 2) {
            lbl_playertwo.setText("➤ "+lbl_playertwo.getText());
        }

        if (zisGame.getPlayers()[0].getStones() < 1 && zisGame.getPlayers()[1].getStones() < 1)
        {
            if (zisGame.checkForWinner() != 0)
            {
                lbl_status.setText("Player "+zisGame.checkForWinner()+" wins.");
                changeButtonAction("null");
            }
        }

    }






    //Soll zum Codesparen dienen. Und zur verbesserten Übersicht.
    //Parameter können sein:
    //place     ->  clickOnFieldToPlace()
    //take      ->  clickOnFieldToTake()
    //(move)    ->  clickOnFieldToShowMoveableFields()
    //null      ->  null
    public void changeButtonAction(String action) {
        for (Button btn_gamebutton : btn_gamebuttons) {
            switch (action) {
                case "place":
                    btn_gamebutton.setOnAction(this::clickOnFieldToPlace); break;
                case "take":
                    btn_gamebutton.setOnAction(this::clickOnFieldToTake); break;
                case "move":
                    btn_gamebutton.setOnAction(this::clickOnFieldToShowMoveableFields); break;
                default:
                    btn_gamebutton.setOnAction(null); break;
            }
        }
    }

    public void resetButtonStyle(){
        for (Button btn_gamebutton : btn_gamebuttons) { btn_gamebutton.setBackground(null); }
    }
}
