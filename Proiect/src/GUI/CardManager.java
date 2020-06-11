package GUI;

import DBManager.DatabaseManager;
import gym_wars.*;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class CardManager {
    private JFrame frame;
    private JButton buttonBack, buttonAdd, buttonDeleteAll, buttonDelete;
    private String db_name;
    private JLabel label;

    private Class<?> getClassOf(String db_name){
        if (db_name.equals("strongman")){
            return Strongman.class;
        }
        if (db_name.equals("trainers")){
            return Trainer.class;
        }
        if (db_name.equals("bodybuilders")){
            return Bodybuilder.class;
        }
        if (db_name.equals("spotters")){
            return Spotter.class;
        }
        return null;
    }

    private void UpdateDB (String db_name) throws IOException, SQLException {
        if (db_name.equals("strongman")){
            Strongman.finalUpdate();
            return;
        }
        if (db_name.equals("trainers")){
            Trainer.finalUpdate();
            return;
        }
        if (db_name.equals("bodybuilders")){
            Bodybuilder.finalUpdate();
            return;
        }
        if (db_name.equals("spotters")){
            Spotter.finalUpdate();
        }
    }

    private void DeleteDB (String db_name) throws IOException, SQLException {
        if (db_name.equals("strongman")){
            Strongman.deleteAllLifters();
            return;
        }
        if (db_name.equals("trainers")){
            Trainer.deleteAllLifters();
            return;
        }
        if (db_name.equals("bodybuilders")){
            Bodybuilder.deleteAllLifters();
            return;
        }
        if (db_name.equals("spotters")){
            Spotter.deleteAllLifters();
        }
    }

    private void ReadDB (String db_name) throws IOException, SQLException {
        if (db_name.equals("strongman")){
            Strongman.readData();
            return;
        }
        if (db_name.equals("trainers")){
            Trainer.readData();
            return;
        }
        if (db_name.equals("bodybuilders")){
            Bodybuilder.readData();
            return;
        }
        if (db_name.equals("spotters")){
            Spotter.readData();
        }
    }

    private String DBtoText (String db_name) throws IOException, SQLException {
        String res = "";
        int it;
        DatabaseManager db_manager = new DatabaseManager();
        ArrayList <Pair<Integer, Integer>> data_list = db_manager.get_lifters(db_name);
        for (it = 0; it < data_list.size(); ++it){
            res = res + String.valueOf(it + 1) + ") " + db_name + ", cost= " +
                    String.valueOf(data_list.get(it).getKey()) +
                    ", power= " + String.valueOf(data_list.get(it).getValue()) +
                    '\n';
        }
        return res;
    }

    private void UpdateLabel () throws IOException, SQLException {
        String label_text = DBtoText(db_name);
        label.setText("<html><pre>" + label_text + "</pre></html>");
    }

    public CardManager(String database_name) throws IOException, SQLException {
        db_name = database_name;
        frame = new JFrame(database_name);
        buttonBack = new JButton("Back to Main Menu");
        buttonAdd = new JButton("Add a Lifter");
        //buttonDelete = new JButton("Delete a Lifter");
        buttonDeleteAll = new JButton("Delete All the Lifters");
        String label_text = DBtoText(db_name);
        label = new JLabel("<html><pre>" + label_text + "</pre></html>");
        frame.add(label);
        frame.add(buttonAdd);
        //frame.add(buttonDelete);
        frame.add(buttonDeleteAll);
        frame.add(buttonBack);
        frame.setSize(300, 600);
        frame.setVisible(true);
        frame.setLayout(new GridLayout(4, 1));
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    UpdateDB(db_name);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                frame.dispose();
            }
        });

        ///Add
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Lifter current;
                try {
                    current = LifterFactory.generate_lifter(db_name);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }

                try {
                    UpdateLabel();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        ///Delete one
        /*buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

            }
        });*/

        ///Delete All
        buttonDeleteAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    DeleteDB(db_name);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
                try {
                    UpdateLabel();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosing(e);
                try {
                    UpdateDB(db_name);
                } catch (IOException | SQLException ioException) {
                    ioException.printStackTrace();
                }
                frame.dispose();
            }
        });
    }
}
