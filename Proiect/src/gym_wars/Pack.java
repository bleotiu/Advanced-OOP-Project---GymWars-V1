package gym_wars;

import RWServices.Auditing;

import java.io.IOException;

public class Pack {
    private Lifter[] cards;
    private int packsize = 49;
    private int next = 0;
    public Pack() throws IOException {
        LifterFactory factory = new LifterFactory();
        cards = new Lifter[packsize];
        for (int i = 0; i < packsize; ++i)
            cards[i] = factory.generate_lifter();
    }
    public Pack(int p_size) throws IOException {
        LifterFactory factory = new LifterFactory();
        packsize = p_size;
        cards = new Lifter[packsize];
        for (int i = 0; i < packsize; ++i)
            cards[i] = factory.generate_lifter();
    }
    public Lifter get_next_lifter(){
        if (next < packsize){
            ++next;
            return cards[next - 1];
        }
        return null;
    }
}
