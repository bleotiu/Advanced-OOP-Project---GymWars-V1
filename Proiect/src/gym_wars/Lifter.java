package gym_wars;

public abstract class Lifter {
    int cost, power;
    boolean is_spotter = false;
    public abstract void ability(Lifter lifter_target, Player player_target);
    public void setCost(int p_cost){
        this.cost = p_cost;
    }
    public void setPower(int p_power){
        this.power = p_power;
    }
    public int getCost(){
        return this.cost;
    }
    public int getPower(){
        return this.power;
    }
    public void ShowCard(){
        String card_name = this.getClass()
                .getCanonicalName().substring(9);
        System.out.println(card_name + " with " + this.power +
                " power and that needs " + this.cost + " protein");
    }
    public boolean IsSpotter(){
        return this.is_spotter;
    }
}