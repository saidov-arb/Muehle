package at.saidov.muehle.model;

public class Player
{
    /**♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦
     * Autor: Arbi Saidov
     * Datum: 21.06.2021
     * Beschreibung: Klasse Player
     ♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦♦*/

    public static int playernr = 0;

    private int stones;
    private String color;

    public int getStones() { return stones; }
    public void setStones(int stones) { this.stones = stones; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public Player() {
        if (playernr == 0) {
            setColor("Blue");
            playernr++;
        }else{
            setColor("Golden");
            playernr = 0;
        }
        setStones(9);
    }

    public Player(int stones,String color) {
        setStones(stones);
        setColor(color);
    }
}
