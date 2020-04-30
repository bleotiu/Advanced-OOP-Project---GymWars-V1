package gym_wars;

import RWServices.Auditing;

import java.io.IOException;

public class EddieHall extends Lifter {
    @Override
    public void ability(Lifter lifter_target, Player player_target) throws IOException {
        Auditing.addAction("ability");
        System.out.println("Wild Eddie Hall appeared");
        System.out.println("Due to extreme levels of dehydration " +
                player_target.getName() + " loses all his gains!");
        System.out.println("I think that is another WORLD RECORD for " +
                "Eddie The Beast Hall!");
        player_target.setGains(0);
    }

    public EddieHall(){
        power = 500;
        cost = 12;
    }
}
