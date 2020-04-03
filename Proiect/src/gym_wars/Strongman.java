package gym_wars;
import java.util.Random;

public class Strongman extends Lifter {
    public static int min_cost = 6, max_cost = 10,
            min_power = 20, max_power = 40;
    @Override
    public void ability(Lifter lifter_target, Player player_target) {
        System.out.println("Strongman attacks player "
                + player_target.getName() + " and deals " +
                this.getPower() + " damage");
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

    public Strongman(){
        Random rand = new Random();
        this.power = min_power +
                rand.nextInt(max_power - min_power + 1);
        this.cost = min_cost +
                rand.nextInt(max_cost - min_cost + 1);
    }

}
