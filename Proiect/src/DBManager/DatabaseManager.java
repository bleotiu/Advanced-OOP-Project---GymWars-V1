package DBManager;

import gym_wars.Lifter;
import javafx.util.Pair;

import RWServices.Auditing;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseManager {
    private Connection db_connector;
    private String db_username;
    private String db_password;
    private String db_url;
    private static String default_url = "jdbc:mysql://localhost:3306/pao";
    private static String default_username = "root";
    private static String default_password = "root";

    public DatabaseManager(){
        db_username = default_username;
        db_url = default_url;
        db_password = default_password;
    }

    public DatabaseManager (String p_url,
                            String p_username,
                            String p_password){
        this.db_username = p_username;
        this.db_password = p_password;
        this.db_url = p_url;
    }

    private void connect() throws SQLException{
        if (db_connector == null || db_connector.isClosed()){
            db_connector = DriverManager.getConnection(db_url,
                    db_username, db_password);
        }
    }

    private void disconnect() throws SQLException{
        if (db_connector != null && !db_connector.isClosed()){
            db_connector.close();
        }
    }

    public boolean exists_in(String db_name, int p_id) throws SQLException {
        if (exists_table(db_name)){
            String sql_command = "SELECT count(*) FROM " + db_name +
                    " WHERE id = " + String.valueOf(p_id + 1) + ";";
            connect();
            Statement db_statement = db_connector.createStatement();
            ResultSet res = db_statement.executeQuery(sql_command);
            res.next();
            int x = res.getInt(1);
            if (x == 0){
                db_statement.close();
                res.close();
                disconnect();
                return false;
            }
            db_statement.close();
            res.close();
            disconnect();
            return true;
        }
        return false;
    }

    public boolean exists_table(String db_name) throws SQLException {
        String sql_command = "SELECT count(*) FROM information_schema.TABLES  WHERE TABLE_NAME = '" + db_name + "';";
        connect();
        PreparedStatement db_statement = db_connector.prepareStatement(sql_command);
        ResultSet res = db_statement.executeQuery(sql_command);
        res.next();
        int x = res.getInt(1);
        if (x == 0){
            db_statement.close();
            res.close();
            disconnect();
            return false;
        }
        db_statement.close();
        res.close();
        disconnect();
        return true;
    }

    public void create_table(String db_name) throws SQLException, IOException {
        Auditing.addAction("create_table", Thread.currentThread().getName());
        if(!exists_table(db_name)){
            String sql_command = "CREATE TABLE `pao`.`" + db_name + "` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `cost` INT NULL,\n" +
                    "  `power` INT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);\n";
            connect();
            Statement db_statement = db_connector.createStatement();
            db_statement.executeUpdate(sql_command);
            db_statement.close();
            disconnect();
        }
    }

    public void insert_lifter (String db_name, int p_cost, int p_power) throws SQLException, IOException {
        Auditing.addAction("insert_lifter", Thread.currentThread().getName());
        if (!exists_table(db_name)){
            create_table(db_name);
        }
        String sql_command = "INSERT INTO " + db_name +
                "(cost, power) VALUES(" + String.valueOf(p_cost) +
                ", " + String.valueOf(p_cost) + ")";
        connect();
        Statement db_statement = db_connector.createStatement();
        db_statement.executeUpdate(sql_command);
        db_statement.close();
        disconnect();
    }

    public ArrayList<Pair<Integer, Integer> > get_lifters (String db_name) throws SQLException, IOException {
        Auditing.addAction("get_lifters", Thread.currentThread().getName());
        if (!exists_table(db_name)){
            create_table(db_name);
        }
        ArrayList<Pair<Integer, Integer> > lifters_list =  new ArrayList<Pair<Integer, Integer> >();
        String sql_command = "Select * from " + db_name;
        connect();

        Statement db_statement = db_connector.createStatement();
        ResultSet res = db_statement.executeQuery(sql_command);

        while (res.next()){
            int cost = res.getInt("cost");
            int power = res.getInt("power");
            Pair<Integer, Integer> p = new Pair<>(cost, power);
            lifters_list.add(p);
        }
        res.close();
        db_statement.close();

        disconnect();
        return lifters_list;
    }

    public void update_lifter (String db_name, int p_id,
                               int new_cost, int new_power) throws SQLException, IOException {
        Auditing.addAction("update_lifter", Thread.currentThread().getName());
        if (exists_in(db_name, p_id)){
            String sql_command = "UPDATE " + db_name + " SET cost = " +
                    String.valueOf(new_cost) + ", power = " +
                    String.valueOf(new_power) + " WHERE id = " +
                    String.valueOf(p_id + 1) + ";";
            connect();
            Statement db_statement = db_connector.createStatement();
            db_statement.executeQuery(sql_command);
            db_statement.close();
            disconnect();
        }
    }

    public void delete_lifter (String db_name, int p_id) throws SQLException, IOException {
        Auditing.addAction("delete_lifter", Thread.currentThread().getName());
        if (exists_in(db_name, p_id)){
            String sql_command = "DELETE from " + db_name + " WHERE id = " +
                    String.valueOf(p_id + 1) + ";";
            connect();
            Statement db_statement = db_connector.createStatement();
            db_statement.executeUpdate(sql_command);
            db_statement.close();
            disconnect();
        }
    }

    public void delete_all_lifters (String db_name) throws SQLException, IOException {
        Auditing.addAction("delete_all_lifters", Thread.currentThread().getName());
        if (exists_table(db_name)){
            String sql_command = "DELETE from " + db_name + " WHERE id > 0;";
            connect();
            Statement db_statement = db_connector.createStatement();
            db_statement.executeUpdate(sql_command);
            db_statement.close();
            disconnect();
        }
    }

    public void delete_table (String db_name) throws SQLException, IOException {
        Auditing.addAction("delete_table", Thread.currentThread().getName());
        if (exists_table(db_name)){
            String sql_command = "DROP TABLE " + db_name + ";";
            connect();
            Statement db_statement = db_connector.createStatement();
            db_statement.executeUpdate(sql_command);
            db_statement.close();
            disconnect();
        }
    }

    public void recreate_table (String db_name, ArrayList<Pair<Integer, Integer> > lifters_gang) throws SQLException, IOException {
        Auditing.addAction("recreate_table", Thread.currentThread().getName());
        if (exists_table(db_name)){
            delete_table(db_name);
        }
        create_table(db_name);
        int it;
        for (it = 0; it < lifters_gang.size(); ++it){
            if (lifters_gang.get(it).getKey() > 0){
                insert_lifter(db_name, lifters_gang.get(it).getKey(), lifters_gang.get(it).getValue());
            }
        }
    }
}
