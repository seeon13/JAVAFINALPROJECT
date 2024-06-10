import java.io.*;
import java.util.*;

public class Player {
    private int code; // back number of player
    private String name; // player name
    private String nationality; // nationality of player
    private int price; // price of player
    private int sort; // position of player (1: ST 2: MF 3: DF 4: GK)

    public Player(int code, String name, String nationality, int price, int sort) {
        this.code = code;
        this.name = name;
        this.nationality = nationality;
        this.price = price;
        this.sort = sort;
    }

    @Override
    public String toString() {
        String[] positions = {"ST", "MF", "DF", "GK"};
        return "[" + code + "] " + name + " | " + nationality + " | [" + positions[sort - 1] + "] | Market Price: " + price;
    }
}