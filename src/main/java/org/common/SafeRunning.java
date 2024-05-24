package org.common;

import java.io.IOException;
import java.sql.SQLException;


public class SafeRunning {
    public static interface SafeRun{
        void run() throws SQLException, IOException;
    }
    public static boolean safe(SafeRun runnable){
        try {
            runnable.run();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
