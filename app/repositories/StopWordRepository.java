package repositories;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.StopWord;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class StopWordRepository {

    private MongoCollection stopWordCollection;

    public StopWordRepository(String databaseName, String collectionName) {
        stopWordCollection = loadMongoCollection(databaseName, collectionName);
    }

    private MongoCollection loadMongoCollection(String databaseName, String collectionName) {
        DB database = new MongoClient().getDB(databaseName);
        Jongo jongo = new Jongo(database);
        return jongo.getCollection(collectionName);
    }

    public List<String> getStopWords() {
        return convertMongoCursorToList(stopWordCollection.find().as(StopWord.class));
    }

    private List<String> convertMongoCursorToList(MongoCursor<StopWord> cursor) {
        List<String> stopWords = new ArrayList<String>();

        while (cursor.hasNext()) {
            stopWords.add(cursor.next().getText());
        }
        return stopWords;
    }
}
