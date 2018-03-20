package repositories;

import com.mongodb.DB;
import org.jongo.Jongo;

public class JongoSingleton {

    private static Jongo instance = null;

    private JongoSingleton() {}

    public static Jongo getInstance(DB database) {
        if (instance == null) {
            instance = new Jongo(database);
        }
        return instance;
    }
}
