package com.company;

import gym_wars.*;
import RWServices.*;
import GUI.*;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void ReadAllData() throws IOException, SQLException {
        Bodybuilder.readData();
        Spotter.readData();
        Strongman.readData();
        Trainer.readData();
    }
    public static void FinalUpdateAll () throws IOException, SQLException {
        Strongman.finalUpdate();
        Spotter.finalUpdate();
        Trainer.finalUpdate();
        Bodybuilder.finalUpdate();
    }
    public static void EndGame (Player P1, Player P2) throws IOException, SQLException {
        if (P1.IsDead()){
            System.out.println(P2.getName() + " has won and" +
                    "is a true lifter!");
        }
        else {
            System.out.println(P1.getName() + " has won and " +
                    "is a true lifter!");
        }
        FinalUpdateAll();
    }
    public static void MatchBetween(Player P1, Player P2) throws IOException, SQLException {
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
    public static void main(String[] args) throws IOException, SQLException {
        //ReadAllData();
        new GUI.Menu();
        /*String name = "Alex";
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
        Strongman.deleteLifter(5);
        Trainer.deleteLifter(7);
        Spotter.deleteLifter(10);
        Bodybuilder.deleteLifter(22);
        Strongman.deleteLifter(100);
        Strongman.editLifter(5, 10, 10);
        Trainer.editLifter(7, 10, 12);
        Spotter.editLifter(10, 22, 32);
        Bodybuilder.editLifter(22, 12, 10);
        FinalUpdateAll();
        MatchBetween (player3, player4);*/
    }
}
