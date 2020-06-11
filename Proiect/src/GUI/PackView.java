package GUI;

import gym_wars.Lifter;
import gym_wars.Pack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;

import static com.company.Main.ReadAllData;

public class PackView {
    JFrame frame;
    JButton buttonBack;
    JLabel label;

    private String PackText () throws IOException, SQLException {
        String line, res = "";
        int it = 1;
        ReadAllData();
        Pack current_pack = new Pack();
        Lifter current = current_pack.get_next_lifter();
        while (current != null){
            line = String.valueOf(it) + ") " +
                    current.LifterType() + ", cost= " +
                    String.valueOf(current.getCost()) +
                    ", power= " + String.valueOf(current.getPower())+ '\n';
            res = res + line;
            current = current_pack.get_next_lifter();
            ++it;
        }
        return res;
    }

    public PackView() throws IOException, SQLException {
        frame = new JFrame("Pack");
        buttonBack = new JButton("Back to Main Menu");
        String label_text = PackText();
        label = new JLabel("<html><pre>" + label_text + "</pre></html>");
        frame.add(label);
        frame.add(buttonBack);
        frame.setSize(300, 600);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(2, 1));
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.dispose();
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                frame.dispose();
            }
        });
    }
}
