package repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class DBSingleton {

    private static DB instance = null;

    private DBSingleton(String databaseName) {}

    public static DB getInstance(String databaseName) {
        if (instance == null) {
            instance = new MongoClient().getDB(databaseName);
        }
        return instance;
    }
}
