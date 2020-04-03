package gym_wars;

import java.util.Random;

public class Bodybuilder extends Lifter {
    public static int min_cost = 1, max_cost = 7,
            min_power = 7, max_power = 15;
    @Override
    public void ability(Lifter lifter_target, Player player_target) {
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

    public Bodybuilder(){
        Random rand = new Random();
        this.power = min_power +
                rand.nextInt(max_power - min_power + 1);
        this.cost = min_cost +
                rand.nextInt(max_cost - min_cost + 1);
    }
}
