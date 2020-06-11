package gym_wars;

import DBManager.DatabaseManager;
import RWServices.Auditing;
import RWServices.ReadWriteService;
import javafx.util.Pair;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Spotter extends Lifter{
    private static ArrayList<Pair<Integer, Integer>> data_csv;
    private static int data_pos = 0;
    private static String fileName = "spotters.csv";
    private static String db_name = "spotters";
    private static boolean deleted_something = false;
    private static int min_cost = 2, max_cost = 5,
            min_power = 3, max_power = 7;
    @Override
    public void ability(Lifter lifter_target, Player player_target) throws IOException {
        Auditing.addAction("ability", Thread.currentThread().getName());
        String spotted = lifter_target.getClass()
                .getCanonicalName().substring(9);
        int new_power = this.power + lifter_target.getPower();
        System.out.println("Spotter is helping a "
                + spotted + " to achieve " +
                 new_power + " power!");
        lifter_target.setPower(new_power);
    }

    public static void readData () throws IOException, SQLException {
        Auditing.addAction("readData", Thread.currentThread().getName());
        //data_csv = ReadWriteService.getInstance().readLifters(fileName);
        DatabaseManager db_manager = new DatabaseManager();
        data_csv = db_manager.get_lifters(db_name);
        if (data_csv.size() > 0)
            Collections.shuffle(data_csv);
        data_pos = 0;
    }

    public Spotter() throws IOException, SQLException {
        if (data_csv == null){
            data_csv = new ArrayList<>();
        }
        this.is_spotter = true;
        while (data_pos < data_csv.size() && data_csv.get(data_pos).getKey() == 0)
            ++data_pos;
        if (data_pos < data_csv.size()){
            this.cost = data_csv.get(data_pos).getKey();
            this.power = data_csv.get(data_pos).getValue();
            ++data_pos;
        }
        else{
            Random rand = new Random();
            this.power = min_power +
                    rand.nextInt(max_power - min_power + 1);
            this.cost = min_cost +
                    rand.nextInt(max_cost - min_cost + 1);
            Pair<Integer, Integer> p = new Pair<>(this.cost, this.power);
            data_csv.add(p);
            DatabaseManager db_manager = new DatabaseManager();
            db_manager.insert_lifter(db_name, this.cost, this.power);
            ++data_pos;
            //ReadWriteService.getInstance().writeLifter(fileName, this.cost, this.power);
        }
    }

    public static void editLifter (int data_pos, int new_cost, int new_power) throws IOException, SQLException {
        if (data_csv == null){
            return;
        }
        if (data_pos < data_csv.size()){
            Pair<Integer, Integer> p = new Pair<>(new_cost, new_power);
            data_csv.set(data_pos, p);
            DatabaseManager db_manager = new DatabaseManager();
            db_manager.update_lifter(db_name, data_pos, new_cost, new_power);
        }
    }

    public static void deleteLifter (int data_pos) throws IOException, SQLException {
        if (data_csv == null){
            return;
        }
        if (data_pos < data_csv.size()){
            deleted_something = true;
            Pair<Integer, Integer> p = new Pair<>(0, 0);
            data_csv.set(data_pos, p);
            DatabaseManager db_manager = new DatabaseManager();
            db_manager.delete_lifter(db_name, data_pos);
        }
    }

    public static void deleteAllLifters () throws IOException, SQLException {
        if(data_csv != null){
            data_pos = 0;
            deleted_something = true;
            data_csv.clear();
            DatabaseManager db_manager = new DatabaseManager();
            db_manager.delete_all_lifters(db_name);
        }
    }

    public static void finalUpdate () throws IOException, SQLException {
        if (data_csv == null){
            data_csv = new ArrayList<>();
        }
        if (deleted_something == true){
            DatabaseManager db_manager = new DatabaseManager();
            db_manager.recreate_table(db_name,data_csv);
            deleted_something = false;
        }
    }
}
