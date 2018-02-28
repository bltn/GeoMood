package repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class TweetRepository {

    private static final String DB_NAME = "geomood";
    private static final DB DATABASE = new MongoClient().getDB(DB_NAME);
    private static final Jongo jongoClient = new Jongo(DATABASE);
    private static final MongoCollection tweetCollection = jongoClient.getCollection("tweets");

    public static boolean save(Tweet tweet) {
        if (tweet.getGeoLocation() != null || tweet.getUserLocation() != null) {
            tweetCollection.save(tweet);
            return true;
        } else {
            return false;
        }
    }
}