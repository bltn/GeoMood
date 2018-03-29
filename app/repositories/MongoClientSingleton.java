package repositories;

import com.mongodb.MongoClient;

public class MongoClientSingleton {

    private static MongoClient instance;

    private MongoClientSingleton() {}

    public static MongoClient getInstance() {
        if (instance == null) {
            instance = new MongoClient();
        }
        return instance;
    }
}
