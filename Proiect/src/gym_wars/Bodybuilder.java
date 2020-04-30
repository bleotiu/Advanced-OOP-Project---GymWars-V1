package gym_wars;

import RWServices.Auditing;
import RWServices.ReadWriteService;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Bodybuilder extends Lifter {
    private static ArrayList<Pair <Integer, Integer>> data_csv;
    private static int min_cost = 1, max_cost = 7,
            min_power = 7, max_power = 15, data_pos = 0;
    private static String fileName = "bodybuilders.csv";
    @Override
    public void ability(Lifter lifter_target, Player player_target) throws IOException {
        Auditing.addAction("ability");
        System.out.println("Bodybuilder attacks player "
                + player_target.getName() + " and deals " +
                this.power + " damage");
        int current_gains = player_target.getGains() - this.power;
        if (current_gains < 0)
            current_gains = 0;
        player_target.setGains(current_gains);
        System.out.println("Now player " + player_target.getName() +
                " only has " + current_gains + " left!");
        if (current_gains == 0){
            System.out.println("Player " + player_target.getName()
                    + " lost all his gains and lost the match!");
        }
    }

    public static void readData () throws IOException {
        Auditing.addAction("readData");
        data_csv = ReadWriteService.getInstance().readLifters(fileName);
        if (data_csv.size() > 0)
            Collections.shuffle(data_csv);
        data_pos = 0;
    }

    public Bodybuilder() throws IOException {
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
