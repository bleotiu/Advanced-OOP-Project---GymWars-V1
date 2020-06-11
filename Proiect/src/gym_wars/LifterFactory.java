package gym_wars;

import RWServices.Auditing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class LifterFactory {
    private static int max_rand = 500001;
    private static int max_spot = 150001;
    private static int max_body = 300001;
    private static int max_train = 420000;
    private static int max_strong = 500000;
    private static String db_spot = "spotters", db_strong = "strongman",
            db_body = "bodybuilders", db_trainer = "trainers";
    public Lifter generate_lifter() throws IOException, SQLException {
        Auditing.addAction("generate_lifter", Thread.currentThread().getName());
        Random rand = new Random();
        int type_number = rand.nextInt(max_rand);
        if (type_number < max_spot){
            return new Spotter();
        } else if (type_number < max_body){
            return new Bodybuilder();
        } else if (type_number < max_train){
            return new Trainer();
        } else if (type_number < max_strong){
            return new Strongman();
        }
        return new EddieHall();
    }

    public static Lifter generate_lifter(String db_name) throws IOException, SQLException {
        Auditing.addAction("generate_lifter", Thread.currentThread().getName());
        if (db_name.equals(db_spot)){
            return new Spotter();
        } else if (db_name.equals(db_body)){
            return new Bodybuilder();
        } else if (db_name.equals(db_trainer)){
            return new Trainer();
        } else if (db_name.equals(db_strong)){
            return new Strongman();
        }
        return new EddieHall();
    }
}
