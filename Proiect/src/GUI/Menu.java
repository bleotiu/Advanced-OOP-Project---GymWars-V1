package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import static com.company.Main.FinalUpdateAll;
import static com.company.Main.ReadAllData;

public class Menu {
    private JFrame frame;
    private JButton buttonQuit, buttonTrainers, buttonSpotters,
            buttonStrongman, buttonBodybuilders, buttonPack;
    private static String db_spot = "spotters", db_strong = "strongman",
            db_body = "bodybuilders", db_trainer = "trainers";

    public Menu() throws IOException, SQLException {
        //ReadAllData();
        frame = new JFrame("GymWars");
        buttonQuit = new JButton("Quit");
        buttonTrainers = new JButton("View Trainers");
        buttonSpotters = new JButton("View Spotters");
        buttonStrongman = new JButton("View Strongmen");
        buttonBodybuilders = new JButton("View Bodybuilders");
        buttonPack = new JButton("Generate a Pack");
        frame.add(buttonPack);
        frame.add(buttonTrainers);
        frame.add(buttonSpotters);
        frame.add(buttonBodybuilders);
        frame.add(buttonStrongman);
        frame.add(buttonQuit);
        frame.setSize(600, 300);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(6, 1));

        buttonQuit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    FinalUpdateAll();
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });

        buttonPack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new PackView();
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonTrainers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new CardManager(db_trainer);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonSpotters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new CardManager(db_spot);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonStrongman.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new CardManager(db_strong);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        buttonBodybuilders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    new CardManager(db_body);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                try {
                    FinalUpdateAll();
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}
