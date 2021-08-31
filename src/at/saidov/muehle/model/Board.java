package at.saidov.muehle.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
    /**♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦
     * Autor: Arbi Saidov
     * Datum: 21.06.2021
     * Beschreibung: Klasse Board
     *          Codes im Feld:
     *              0       =       Frei
     *              1       =       Spieler 1
     *              2       =       Spieler 2
     *              11      =       Spieler 1 Mühle Element
     *              22      =       Spieler 2 Mühle Element
     *              7       =       Nicht Spiel Feld
     ♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦*/

    //Das Feld ist Nunmal 7*7 Groß.
    public final static int FIELDSIZE = 7;

    private int[][] field;

    public int[][] getField() { return field; }
    public void setField(int[][] field) { this.field = field; }


    public Board() {
        setField(setUpNewField());
    }

    public Board(int[][] iField) {
        setField(iField);
    }



    //Methode zum setzen von Stein ins Field.
    public void setStone(int x, int y, int nr)
    {
        try
        {
            getField()[y][x] = nr;
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Dings, das Feld existiert nicht.");
        }
    }



    //Methode zum bewegen von Stein im Field.
    public void moveStone(int x1, int y1, int x2, int y2)
    {
        getField()[y2][x2] = getField()[y1][x1];
        getField()[y1][x1] = 0;
    }



    //Methode zum entnehmen von Stein vom Field.
    public void removeStone(int x, int y)
    {
        try
        {
            if (checkForStone(x,y))
            {
                getField()[y][x] = 0;
            }else
            {
                System.out.println("Hier befindet sich kein Stein.");
            }
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Dings, das Feld existiert nicht.");
        }
    }



    //Methode zum kontrollieren, ob Stein ins Field gesetzt werden darf.
    public boolean checkForSpace(int x, int y)
    {
        try
        {
            return getField()[y][x] == 0;
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Dings, das Feld existiert nicht.");
        }
        return false;
    }



    //Methode zum kontrollieren, ob Stein vom Field genommen werden darf.
    public boolean checkForStone(int x, int y)
    {
        try
        {
            return getField()[y][x] == 1 || getField()[y][x] == 2;
        }catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Dings, das Feld existiert nicht.");
        }
        return false;
    }



    //Methode, die alle Möglichkeiten zum Bewegen eines Steins in einer ArrayList, in einzelnen
    //2 Dimensionalen Arrays zurück gibt.
    //Stelle 0 = x Achse
    //Stelle 1 = y Achse
    public ArrayList<int[]> getPossibilitiesForMovement(int x, int y)
    {
        ArrayList<int[]> possibilities = new ArrayList<>();


        //Scan waagrecht nach rechts.
        for (int i = 1; i < FIELDSIZE; i++)
        {
            try
            {
                if (y != 3)
                {
                    if (getField()[y][x + i] != 7)
                    {
                        if (getField()[y][x + i] == 0)
                        {
                            possibilities.add(new int[]{x + i, y});
                            break;
                        } else if (getField()[y][x + i] != 0)
                        {
                            break;
                        }
                    }
                }else
                {
                    if (getField()[y][x+i] == 0)
                    {
                        possibilities.add(new int[] {x + i, y});
                        break;
                    } else if (getField()[y][x + i] != 0)
                    {
                        break;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Scan waggrecht nach rechts hat Ende erreicht.");
                break;
            }
        }



        //Scan waagrecht nach links.
        for (int i = 1; i < FIELDSIZE; i++)
        {
            try{
                if (y != 3)
                {
                    if (getField()[y][x - i] != 7)
                    {
                        if (getField()[y][x - i] == 0)
                        {
                            possibilities.add(new int[]{x - i, y});
                            break;
                        } else if (getField()[y][x - i] != 0)
                        {
                            break;
                        }
                    }
                }else
                {
                    if (getField()[y][x - i] == 0)
                    {
                        possibilities.add(new int[] {x - i, y});
                        break;
                    } else if (getField()[y][x - i] != 0)
                    {
                        break;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Scan waggrecht nach links hat Ende erreicht.");
                break;
            }
        }



        //Scan senkrecht nach unten.
        for (int i = 1; i < FIELDSIZE; i++)
        {
            try{
                if (x != 3)
                {
                    if (getField()[y + i][x] != 7)
                    {
                        if (getField()[y + i][x] == 0)
                        {
                            possibilities.add(new int[]{x, y + i});
                            break;
                        } else if (getField()[y + i][x] != 0)
                        {
                            break;
                        }
                    }
                }else {
                    if (getField()[y + i][x] == 0)
                    {
                        possibilities.add(new int[]{x, y + i});
                        break;
                    } else if (getField()[y + i][x] != 0)
                    {
                        break;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Scan senkrecht nach unten hat Ende erreicht.");
                break;
            }
        }



        //Scan senkrecht nach oben.
        for (int i = 1; i < FIELDSIZE; i++)
        {
            try{
                if (x != 3)
                {
                    if (getField()[y - i][x] != 7)
                    {
                        if (getField()[y - i][x] == 0)
                        {
                            possibilities.add(new int[]{x, y - i});
                            break;
                        } else if (getField()[y - i][x] != 0)
                        {
                            break;
                        }
                    }
                }else{
                    if (getField()[y - i][x] == 0)
                    {
                        possibilities.add(new int[]{x, y - i});
                        break;
                    } else if (getField()[y - i][x] != 0)
                    {
                        break;
                    }
                }
            }catch (ArrayIndexOutOfBoundsException e){
                System.out.println("Scan senkrecht nach oben hat Ende erreicht.");
                break;
            }
        }

        return possibilities;
    }




    //Der Return Wert ist ein Spielfeld, wo die Nicht Spiel Felder mit 7 befüllt sind, die Spielfelder mit 0.
    int[][] setUpNewField()
    {
        int[][] reField = new int[FIELDSIZE][FIELDSIZE];
        int x,y;

        //Erstmal alles auf Nicht Spiel Feld.
        for (int i = 0; i < FIELDSIZE; i++)
        {
            for (int j = 0; j < FIELDSIZE; j++)
            {
                reField[i][j] = 7;
            }
        }



        //Dann die schiefen (nach unten rechts) auf 0 setzen.
        y = 0;
        x = 0;

        while(y < FIELDSIZE)
        {
            if ((x==y) && (y!=3))
            {
                reField[y][x] = 0;
            }
            y++;
            x++;
        }



        //Dann die schiefen (nach unten links) auf 0 setzen.
        y=0;
        x=FIELDSIZE-1;

        while(y < FIELDSIZE)
        {
            if (y!=3)
            {
                reField[y][x] = 0;
            }
            y++;
            x--;
        }



        //Dann die senkrechten von x = 3 auf 0 setzen.

        y=0;
        x=3;

        while(y < FIELDSIZE)
        {
            if (y!=3)
            {
                reField[y][x] = 0;
            }
            y++;
        }



        //Dann die waagrechten von y = 3 auf 0 setzen.

        y=3;
        x=0;

        while(x < FIELDSIZE)
        {
            if (x!=3)
            {
                reField[y][x] = 0;
            }
            x++;
        }


        return reField;
    }



    //Gibt das Spielfeld aus, inklusive Nicht Spiel Felder.
    @Override
    public String toString()
    {
//        String reString = "\n0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 |\n";
        StringBuilder reString = new StringBuilder("\n");
        for (int i = 0; i < FIELDSIZE; i++)
        {
//            reString += (i+1)+" | ";
            for (int j = 0; j < FIELDSIZE; j++)
            {
                switch (getField()[i][j]){
                    case 0:
                        reString.append((char) 27 + "[36m").append(getField()[i][j]).append("   ");
                        break;
                    case 7:
                        reString.append((char) 27 + "[31m").append(getField()[i][j]).append("   ");
                        break;
                    case 1:
                        reString.append((char) 27 + "[34m").append(getField()[i][j]).append("   ");
                        break;
                    case 2:
                        reString.append((char) 27 + "[32m").append(getField()[i][j]).append("   ");
                        break;
                    default:
                        reString.append(getField()[i][j]).append(" | ");
                        break;
                }
//                if (getField()[i][j] == 0)
//                {
//                    reString += (char)27+"[36m"+getField()[i][j] + "   ";
//                }else if (getField()[i][j] == 7)
//                {
//                    reString += (char)27 + "[31m" +getField()[i][j] + "   ";
//                }else if (getField()[i][j] == 1)
//                {
//                    reString += (char)27+"[34m"+getField()[i][j] + "   ";
//                }else if (getField()[i][j] == 2)
//                {
//                    reString += (char)27+"[32m"+getField()[i][j] + "   ";
//                }else{
//                    reString += getField()[i][j] + " | ";
//                }
            }
            reString.append((char) 27 + "[38m" + "\n");
        }
        return reString.toString();
    }
}
