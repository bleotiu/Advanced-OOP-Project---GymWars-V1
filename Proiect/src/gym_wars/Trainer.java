package gym_wars;

import java.util.Random;

public class Trainer extends Lifter {
    public static int min_cost = 3, max_cost = 7,
            min_power = 5, max_power = 10;
    @Override
    public void ability(Lifter lifter_target, Player player_target) {
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
    public Trainer(){
        Random rand = new Random();
        this.power = min_power +
                rand.nextInt(max_power - min_power + 1);
        this.cost = min_cost +
                rand.nextInt(max_cost - min_cost + 1);
    }
}
