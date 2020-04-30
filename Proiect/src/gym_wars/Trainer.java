package gym_wars;

import RWServices.Auditing;
import RWServices.ReadWriteService;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Trainer extends Lifter {
    private static ArrayList<Pair<Integer, Integer>> data_csv;
    private static int data_pos = 0;
    private static String fileName = "trainers.csv";
    private static int min_cost = 3, max_cost = 7,
            min_power = 5, max_power = 10;
    @Override
    public void ability(Lifter lifter_target, Player player_target) throws IOException {
        Auditing.addAction("ability");
        int diff = 0, current_gains = player_target.getGains() + this.power;
        if (current_gains > Player.start_gains){
            diff = current_gains - Player.start_gains;
            current_gains -= diff;
        }
        System.out.println("Trainer heals player "
                + player_target.getName() + " and gives him " +
                (this.getPower() - diff) + " gains");
        player_target.setGains(current_gains);
        System.out.println("Now player " + player_target.getName() +
                " has " + current_gains + " gains!");
    }

    public static void readData () throws IOException {
        Auditing.addAction("readData");
        data_csv = ReadWriteService.getInstance().readLifters(fileName);
        if (data_csv.size() > 0)
            Collections.shuffle(data_csv);
        data_pos = 0;
    }

    public Trainer() throws IOException {
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
            ReadWriteService.getInstance().writeLifter(fileName, this.cost, this.power);
        }
    }
}
