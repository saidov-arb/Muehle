package at.saidov.muehle.model;

import java.util.ArrayList;
import java.util.Arrays;

public class Game
{
    /**♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦
     * Autor: Arbi Saidov
     * Datum: 21.06.2021
     * Beschreibung: Klasse Game
     *          Im ControllerGame soll nur ein Objekt dieser Klasse
     *          erstellt werden und das ganze Spiel auf diesem
     *          einen Objekt laufen.
     *          Gemäß MVC natürlich!!!
     ♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦*/

    private Player[] players = new Player[2];
    private Board board;

    public int playercounter = 1;

    //Woher ich weiß, dass es 16 3 2 ist?
    //Es gibt in Mühle insgesamt 16 Mühlen, die man bauen kann.
    //Jede Mühle besteht aus 3 Steinen.
    //Für die Position von jedem Stein brauche ich 2 Achsenstellen.
    //=> 16 Mühlen; 3 Steine; 2 Stellen;
    public static int[][][] mills = new int[16][3][2];

    public Player[] getPlayers() { return players; }
    public void setPlayers(Player[] players) { this.players = players; }
    public Board getBoard() { return board; }
    public void setBoard(Board board) { this.board = board; }


    public Game() {
        getPlayers()[0] = new Player();
        getPlayers()[1] = new Player();
        setBoard(new Board());
        fillMills();
    }

    public Game(Player[] iPlayers, Board iBoard){
        setPlayers(iPlayers);
        setBoard(iBoard);
        fillMills();
    }

    public Game(Player playerone, Player playertwo, Board iBoard){
        getPlayers()[0] = playerone;
        getPlayers()[1] = playertwo;
        setBoard(iBoard);
        fillMills();
    }


    //Füllt das statische Array "mills", mit allen Mühlen, die man auf dem Feld bauen kann.
    //Wird nur im Konstruktor aufgerufen.
    void fillMills(){
        Board iBoard = new Board();
        int millSlotCounter = 0;
        int millCounter = 0;

        //Waagrechte UND Senkrechte Mühlen definieren.
        for (int i = 0; i < Board.FIELDSIZE; i++) {
            for (int j = 0; j < Board.FIELDSIZE; j++) {
                if (iBoard.getField()[i][j] == 0) {
                    mills[millCounter][millSlotCounter][0] = j;
                    mills[millCounter][millSlotCounter][1] = i;
                    mills[millCounter+1][millSlotCounter][0] = i;
                    mills[millCounter+1][millSlotCounter][1] = j;
                    millSlotCounter++;
                    if (millSlotCounter == 3) {
                        millSlotCounter = 0;

                        //Wenn i=3 ist, befindet man sich ja in der Mitte. Da sind leider Gottes 2 Mülen, die man
                        //schließen könnte. Deswegen ist folgender if hier drinnen.
                        if (i == 3) {
                            millCounter+=2;
                            j++;
                            while(j < Board.FIELDSIZE){
                                if (iBoard.getField()[i][j] == 0) {
                                    mills[millCounter][millSlotCounter][0] = j;
                                    mills[millCounter][millSlotCounter][1] = i;
                                    mills[millCounter+1][millSlotCounter][0] = i;
                                    mills[millCounter+1][millSlotCounter][1] = j;
                                    millSlotCounter++;
                                    if (millSlotCounter == 3) {
                                        millSlotCounter = 0;
                                        break;
                                    }
                                }
                                j++;
                            }
                        }
                        break;
                    }
                }
            }
            millCounter+=2;
        }

//        for (int i = 0; i < mills.length; i++)
//        {
//            System.out.println("\n\nMill "+i+": ");
//            for (int j = 0; j < mills[i].length; j++)
//            {
//                System.out.print("\n\tSlot "+j+": ");
//                System.out.print("\n\t\tPunkt: ("+mills[i][j][0]+"|"+mills[i][j][1]+")");
//            }
//        }
//        System.out.println("\n\n\n");
    }



    //Methode zum platzieren von Stein.
    public void placeStone(int x, int y)
    {
        if (getBoard().checkForSpace(x,y)){
            getBoard().setStone(x,y,playercounter);
            getPlayers()[playercounter-1].setStones(getPlayers()[playercounter-1].getStones()-1);
        }
        else{
            System.out.println("Platzieren nicht erlaubt.");
        }
        System.out.println(toString());
    }

    //Methode zum entnehmen von Stein.
    public void takeStone(int x, int y)
    {
        if (getBoard().checkForStone(x,y)){
            if (!checkIfMillClosed(x,y))
            {
                getBoard().removeStone(x, y);
            }else {
                System.out.println("Geschlossene Mühle kannst du nicht angreifen.");
            }
        }
        System.out.println(toString());
    }

    //Methode zum bewegen von Stein.
    public void moveStone(int x1, int y1, int x2, int y2)
    {
        ArrayList<int[]> possibilities = getBoard().getPossibilitiesForMovement(x1,y1);
        if(possibilities.size() >= 1){
            for (int[] possibility : possibilities)
            {
                if (possibility[0] == x2 && possibility[1] == y2)
                {
                    getBoard().moveStone(x1, y1, x2, y2);
                }
            }
        }else{
            System.out.println("Verschieben nicht möglich.");
        }
        System.out.println(toString());
    }


    public boolean checkIfMillClosed(int x,int y){
        ArrayList<int[][]> possibleMills = new ArrayList<>();
        int[] coordinates = {x,y};

        for (int i = 0; i < mills.length; i++)
        {
            for (int j = 0; j < mills[i].length; j++)
            {
                if (mills[i][j][0] == x && mills[i][j][1] == y)
                {
                    possibleMills.add(mills[i]);
                }
            }
        }

        for (int[][] possibleMill : possibleMills)
        {
            if (getBoard().getField()[possibleMill[0][1]][possibleMill[0][0]] ==
                    getBoard().getField()[possibleMill[1][1]][possibleMill[1][0]] &&
                    getBoard().getField()[possibleMill[1][1]][possibleMill[1][0]] ==
                            getBoard().getField()[possibleMill[2][1]][possibleMill[2][0]])
            {
                return true;
            }
        }

//        for (int i = 0; i < possibleMills.size(); i++)
//        {
//            System.out.println("\n\nMill "+i+": ");
//            for (int j = 0; j < possibleMills.get(i).length; j++)
//            {
//                System.out.print("\n\tSlot "+j+": ");
//                System.out.print("\n\t\tPunkt: ("+possibleMills.get(i)[j][0]+"|"+possibleMills.get(i)[j][1]+")");
//            }
//        }
//        System.out.println("\n\n\n");

        return false;
    }


    public void updatePlayerCounter()
    {
        if (playercounter == 1) {
            playercounter++;
        } else if (playercounter == 2) {
            playercounter = 1;
        }
    }


    @Override
    public String toString()
    {
        return "\n\n--PlayerOne-- Stones: "+getPlayers()[0].getStones()+
                "\n--PlayerTwo-- Stones: "+getPlayers()[1].getStones()+
                "\n"+getBoard().toString();
    }
}
