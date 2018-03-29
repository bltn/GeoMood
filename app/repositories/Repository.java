package repositories;

import com.mongodb.DB;
import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public abstract class Repository {

    private DB database;
    private Jongo jongoDriver;
    protected final MongoCollection collection;

    public Repository(String dbName, String collectionName) {
        database = MongoClientSingleton.getInstance().getDB(dbName);
        jongoDriver = new Jongo(database);
        collection = jongoDriver.getCollection(collectionName);
    }

    public long getCount() {
        return collection.count();
    }

    public Find findAll() {
        return collection.find();
    }
}