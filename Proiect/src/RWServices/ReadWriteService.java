package RWServices;


import javafx.util.Pair;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ReadWriteService {
    private static ReadWriteService instance = null;

    private ReadWriteService() {}

    public static ReadWriteService getInstance(){
        if (instance == null){
            instance = new ReadWriteService();
        }
        return instance;
    }

    public ArrayList<Pair<Integer, Integer> > readLifters (String path) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(path));
        String line;
        String[] datas;
        ArrayList<Pair<Integer, Integer> > res =  new ArrayList<Pair<Integer, Integer> >();
        while ((line = input.readLine()) != null){
            datas = line.split(",");
            int A = Integer.parseInt(datas[0]), B = Integer.parseInt(datas[1]);
            Pair<Integer, Integer> p = new Pair<>(A, B);
            res.add(p);
        }
        input.close();
        return res;
    }

    public void writeLifter (String path, int cost, int power) throws IOException {
        FileWriter output = new FileWriter(path, true);
        output.write (cost + "," + power + "\n");
        output.close();
    }

    public void writeAction (String path, String action_name, String thread_name) throws IOException {
        FileWriter output = new FileWriter(path, true);
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        String when = ts.toString();
        output.write(action_name + "()," + when + "," + thread_name + "\n");
        output.close();
    }
}
