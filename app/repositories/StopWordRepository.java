package repositories;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import javafx.scene.paint.Stop;
import models.StopWord;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;

public class StopWordRepository extends Repository {

    public StopWordRepository(String dbName, String collectionName) {
        super(dbName, collectionName);
    }

    public List<String> getStopWords() {
        return convertMongoCursorToList(findAll().as(StopWord.class));
    }

    private List<String> convertMongoCursorToList(MongoCursor<StopWord> cursor) {
        List<String> stopWords = new ArrayList<String>();

        while (cursor.hasNext()) {
            stopWords.add(cursor.next().getText());
        }
        return stopWords;
    }
}
