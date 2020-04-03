package gym_wars;
import org.w3c.dom.ls.LSOutput;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private static int hand_size = 5;
    int gains, protein;
    String name;
    public static int start_gains = 100, start_protein = 0;
    Pack lifters_gang;
    ArrayList <Lifter> hand;
    ArrayList <Lifter> zone;
    Player opponent;

    public Player(){
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
    public Player(String p_name){
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
        for (int i = 0; i < s.length(); ++i)
            if (s.charAt(i) != '-' && ('0' > s.charAt(i) ||
                    '9' < s.charAt(i)))
                return false;
        return true;
    }

    int CheckMove (int X, int Y){
        if (X < 0 || X >= this.hand.size())
            return 0;
        if (hand.get(X).is_spotter && Y > this.hand.size() - 1 &&
                Y < this.hand.size() + this.zone.size())
            return 1;
        if (!hand.get(X).is_spotter && (Y == 1000 || Y == -1000))
            return 1;
        return 0;
    }

    public void ShowCards(){
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
        int valid = 0, x = 0, y = 0;
        while (valid == 0){
            System.out.println("If you don't know how to make an action " +
                    "type in the word \"action\"");
            current = input.nextLine();
            while (current == "action"){
                HowTo();
                current = input.nextLine();
            }
            arr = current.split(" ");
            if (IsNumber(arr[0]) && IsNumber(arr[1])){
                x = Integer.parseInt(arr[0]);
                y = Integer.parseInt(arr[1]);
                valid = CheckMove (x, y);
            }
        }
        if (this.hand.get(x).is_spotter)
            this.hand.get(x).ability(this.zone.get(y), null);
        else{
            if(y > 0)
                this.hand.get(x).ability(null, this);
            else
                this.hand.get(x).ability(null, opponent);
            this.zone.add(this.hand.get(x));
            this.hand.remove(x);
        }

    }

    public void HowTo(){
        System.out.println("On each action you have to introduce " +
                "2 distinct numbers with only one space between them");
        System.out.println("The first number is the number of the" +
                "card you want to use");
        System.out.println("The second number will be the target " +
                "of that card");
        System.out.println("If your card is a spotter you have to" +
                "write the number of the card in the battle zone" +
                "that you want it to help");
        System.out.println("Otherwise if you want the card to have" +
                "effect on you you write 1000 and if you want the" +
                "card to have effect on your opponent you type in " +
                "-1000");
    }

    public boolean IsDead(){
        if (this.gains == 0)
            return true;
        return false;
    }

    public void Quit(){
        System.out.println("Player " + this.name +
                " is a pussy and quits!");
        this.gains = 0;
    }
}
