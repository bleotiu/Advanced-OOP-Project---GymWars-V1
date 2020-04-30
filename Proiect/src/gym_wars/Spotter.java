package gym_wars;

import RWServices.Auditing;
import RWServices.ReadWriteService;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Spotter extends Lifter{
    private static ArrayList<Pair<Integer, Integer>> data_csv;
    private static int data_pos = 0;
    private static String fileName = "spotters.csv";
    private static int min_cost = 2, max_cost = 5,
            min_power = 3, max_power = 7;
    @Override
    public void ability(Lifter lifter_target, Player player_target) throws IOException {
        Auditing.addAction("ability");
        String spotted = lifter_target.getClass()
                .getCanonicalName().substring(9);
        int new_power = this.power + lifter_target.getPower();
        System.out.println("Spotter is helping a "
                + spotted + " to achieve " +
                 new_power + " power!");
        lifter_target.setPower(new_power);
    }

    public static void readData () throws IOException {
        Auditing.addAction("readData");
        data_csv = ReadWriteService.getInstance().readLifters(fileName);
        if (data_csv.size() > 0)
            Collections.shuffle(data_csv);
        data_pos = 0;
    }

    public Spotter() throws IOException {
        this.is_spotter = true;
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
