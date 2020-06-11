package RWServices;

import java.io.IOException;

public class Auditing {
    private static String fileName = "audit.csv";
    public static void addAction (String action_name, String thread_name) throws IOException {
        ReadWriteService.getInstance().writeAction(fileName, action_name, thread_name);
    }
}
