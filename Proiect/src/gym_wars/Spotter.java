package gym_wars;

import java.util.Random;

public class Spotter extends Lifter{
    public static int min_cost = 2, max_cost = 5,
            min_power = 3, max_power = 7;
    @Override
    public void ability(Lifter lifter_target, Player player_target) {
        String spotted = lifter_target.getClass()
                .getCanonicalName().substring(9);
        int new_power = this.power + lifter_target.getPower();
        System.out.println("Spotter is helping a "
                + spotted + " to achieve " +
                 new_power + " power!");
        lifter_target.setPower(new_power);
    }

    public Spotter(){
        this.is_spotter = true;
        Random rand = new Random();
        this.power = min_power +
                rand.nextInt(max_power - min_power + 1);
        this.cost = min_cost +
                rand.nextInt(max_cost - min_cost + 1);
    }
}
