package com.company;

import gym_wars.*;

public class Main {
    public static void EndGame (Player P1, Player P2){
        if (P1.IsDead()){
            System.out.println(P2.getName() + " has won and" +
                    "is a true lifter!");
        }
        else {
            System.out.println(P1.getName() + " has won and" +
                    "is a true lifter!");
        }
    }
    public static void MatchBetween(Player P1, Player P2){
        P1.setOpponent(P2);
        P2.setOpponent(P1);
        while (!P1.IsDead() && !P2.IsDead()){
            P1.drawCard();
            P1.ShowCards();
            if (P1.IsDead() || P2.IsDead())
                EndGame(P1, P2);
            else{
                P2.drawCard();
                P2.ShowCards();
                if (P1.IsDead() || P2.IsDead())
                    EndGame(P1, P2);
            }
        }
    }
    public static void main(String[] args) {
        String name = "Alex";
        Player player1 = new Player(name);
        Player player2 = new Player("John");
        Strongman strong1 = new Strongman();
        Trainer train1 = new Trainer();
        Spotter spot1 = new Spotter();
        Bodybuilder body1 = new Bodybuilder();
        EddieHall beast = new EddieHall();
        System.out.println("Some random actions");
        player1.setName("Andone");
        spot1.ability(body1, null);
        spot1.ability(body1, null);
        body1.ability(null, player2);
        strong1.ability(null, player1);
        train1.ability(null, player1);
        spot1.ability(strong1, null);
        strong1.ability(null, player1);
        train1.ability(null, player1);
        train1.ability(null, player2);
        beast.ability(null, player2);
        player1.Quit();
        LifterFactory factory = new LifterFactory();
        System.out.println("Now we will generate 10 random cards");
        Lifter[] arrLifters = new Lifter[10];
        for (int i = 0; i < 10; ++i){
            arrLifters[i] = factory.generate_lifter();
            arrLifters[i].ShowCard();
        }
        Player player3 = new Player("Brian");
        Player player4 = new Player("Sam");
        MatchBetween (player3, player4);
    }
}
