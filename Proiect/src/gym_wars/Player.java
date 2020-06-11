package gym_wars;

import RWServices.Auditing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private static int hand_size = 5;
    private int gains, protein;
    private String name;
    public static int start_gains = 100, start_protein = 0;
    private Pack lifters_gang;
    private ArrayList <Lifter> hand;
    private ArrayList <Lifter> zone;
    private Player opponent;

    public Player() throws IOException, SQLException {
        System.out.println("Enter the player name :");
        Scanner input = new Scanner(System.in);
        this.name = input.nextLine();
        System.out.println("Player " + this.name + " entered the game!");
        gains = start_gains;
        protein = start_protein;
        lifters_gang = new Pack();
        hand = new ArrayList<>();
        for (int i = 0; i < hand_size; ++i)
            hand.add(lifters_gang.get_next_lifter());
        zone = new ArrayList<>();
    }
    public Player(String p_name) throws IOException, SQLException {
        this.name = p_name;
        System.out.println("Player " + this.name + " entered the game!");
        gains = start_gains;
        protein = start_protein;
        lifters_gang = new Pack();
        hand = new ArrayList<>();
        for (int i = 0; i < hand_size; ++i)
            hand.add(lifters_gang.get_next_lifter());
        zone = new ArrayList<>();
    }

    public String getName(){
        return this.name;
    }
    public int getGains() {
        return this.gains;
    }
    public int getProtein() {
        return this.protein;
    }
    public Player getOpponent(){
        return this.opponent;
    }
    public void setGains(int gains) {
        this.gains = gains;
    }
    public void setName(String name) {
        System.out.println("Now player " + this.name +
                " is called " + name);
        this.name = name;
    }
    public void setProtein(int protein) {
        this.protein = protein;
    }
    public void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    public void drawCard (){
        this.hand.add(lifters_gang.get_next_lifter());
    }

    boolean IsNumber (String s){
        if (s.isEmpty() || s.charAt(0) != '-' && ('0' > s.charAt(0) ||
                '9' < s.charAt(0)))
            return false;
        for (int i = 1; i < s.length(); ++i)
            if ('0' > s.charAt(i) || '9' < s.charAt(i))
                return false;
        return true;
    }

    int CheckMove (int X, int Y) throws IOException {
        Auditing.addAction("CheckMove", Thread.currentThread().getName());
        if (X < 0 || X >= this.hand.size() + this.zone.size())
            return 0;
        if (X < hand.size() && hand.get(X).IsSpotter() && Y > this.hand.size() - 1 &&
                Y < this.hand.size() + this.zone.size())
            return 1;
        if (X < hand.size() && !hand.get(X).IsSpotter() && (Y == 1000 || Y == -1000))
            return 1;
        if (X < hand.size() + zone.size() && !zone.get(X - hand.size()).IsSpotter()
            && (Y == 1000 || Y == -1000))
            return 1;
        return 0;
    }

    public void ShowCards() throws IOException {
        Auditing.addAction("ShowCards", Thread.currentThread().getName());
        ///Here a player is presented with the list of his cards
        ///and he has to make a move for the game to continue
        System.out.println(this.name + " has the following cards:");
        for (int i = 0; i < hand.size(); ++i){
            System.out.print(i + ") ");
            hand.get(i).ShowCard();
        }
        if (zone.size() == 0){
            System.out.println("He has no cards in the battle zone");
        }
        else{
            System.out.println("And in the battle zone he has:");
            for (int i = 0; i < zone.size(); ++i){
                System.out.print((i + hand.size()) + ") ");
                zone.get(i).ShowCard();
            }
        }
        Scanner input = new Scanner(System.in);
        String current;
        String[] arr;
        Lifter current_card;
        int valid = 0, x = 0, y = 0;
        while (valid == 0){
            System.out.println("If you don't know how to make an action " +
                    "type in the word \"action\"");
            current = input.nextLine();
            //System.out.println("Am citit " + current);
            while (current.equals("action") || current.equals("quit")){
                if (current.equals("quit")){
                    Quit();
                    return;
                }
                HowTo();
                current = input.nextLine();
            }

            arr = current.split(" ");
            x = arr.length;
            //System.out.println(x);
            if (x > 1 && IsNumber(arr[0]) && IsNumber(arr[1])){
                x = Integer.parseInt(arr[0]);
                y = Integer.parseInt(arr[1]);
                //System.out.println("am citit " + x + " si " + y);
                valid = CheckMove (x, y);
            }
        }
        if (x < hand.size())
            current_card = hand.get(x);
        else
            current_card = zone.get(x - hand.size());
        if (current_card.is_spotter) {
            current_card.ability(this.zone.get(y), null);
            hand.remove(x);
        }
        else {
            if (y > 0)
                current_card.ability(null, this);
            else
                current_card.ability(null, opponent);
            if (x < hand.size()) {
                this.zone.add(current_card);
                this.hand.remove(x);
            }
        }
    }

    public void HowTo() throws IOException {
        Auditing.addAction("HowTo", Thread.currentThread().getName());
        System.out.println("On each action you have to introduce " +
                "2 distinct numbers with only one space between them");
        System.out.println("The first number is the number of the " +
                "card you want to use");
        System.out.println("The second number will be the target " +
                "of that card");
        System.out.println("If your card is a spotter you have to " +
                "write the number of the card in the battle zone " +
                "that you want it to help");
        System.out.println("Otherwise if you want the card to have " +
                "effect on you you write 1000 and if you want the " +
                "card to have effect on your opponent you type in " +
                "-1000");
        System.out.println("If you want to quit the game type in the word quit");
    }

    public boolean IsDead(){
        if (this.gains == 0)
            return true;
        return false;
    }

    public void Quit() throws IOException {
        Auditing.addAction("Quit", Thread.currentThread().getName());
        System.out.println("Player " + this.name +
                " is a pussy and quits!");
        this.gains = 0;
    }
}
