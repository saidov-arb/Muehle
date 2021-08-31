package at.saidov.muehle.themain;

import at.saidov.muehle.model.Board;
import at.saidov.muehle.model.Game;
import at.saidov.muehle.model.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /**♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦
     * Autor: Arbi Saidov
     * Datum: 21.06.2021
     * Beschreibung:
     *          Das Mühle Spiel von Arbi Saidov.
     *          Für die Bessere Noten von den Zwischennoten 2 - 3.
     *
     *          Gute Quelle:
     *              https://docplayer.org/25635393-Projekt-muehle-gesellschaftsspiel.html
     *
     *          Möge Gott mir beistehen.
     ♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦*/

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../viewcontrol/mainmenu/StartScreen.fxml"));
        primaryStage.setTitle("Muehle van Saidov");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Game iGame = new Game();
        iGame.getPlayers()[0] = new Player();
        iGame.getPlayers()[1] = new Player();
        iGame.setBoard(new Board());

        System.out.println("--PlayerOne--: Color: "+iGame.getPlayers()[0].getColor());
        System.out.println("--PlayerTwo--: Color: "+iGame.getPlayers()[1].getColor());

        System.out.println(iGame.toString());

        iGame.placeStone(0,0);
        iGame.updatePlayerCounter();

        iGame.placeStone(1,1);
        iGame.updatePlayerCounter();

        iGame.placeStone(3,0);
        iGame.updatePlayerCounter();

        iGame.placeStone(0,3);
        iGame.updatePlayerCounter();

        iGame.placeStone(6,0);
        iGame.updatePlayerCounter();

        iGame.placeStone(1,5);
        iGame.updatePlayerCounter();

        iGame.placeStone(6,6);
        iGame.updatePlayerCounter();

        iGame.placeStone(2,3);
        iGame.updatePlayerCounter();

        iGame.placeStone(6,3);
        iGame.updatePlayerCounter();

        iGame.placeStone(3,6);
        iGame.updatePlayerCounter();

        iGame.moveStone(0,3,1,3);


        if (iGame.checkIfMillClosed(3,0))
        {
            System.out.println("Jawohl, Mühle geschlossen!");
            iGame.takeStone(2,3);
        }



        launch(args);
    }
}
