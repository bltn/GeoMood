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
        boolean shouldBeSaved = false;

        // save if it has a geolocation
        if (tweet.getGeoLocation() != null) shouldBeSaved = true;

        // save if it has a non-empty user profile location
        if (tweet.getUserLocation() != null) {
            if (!tweet.getUserLocation().trim().isEmpty()) shouldBeSaved = true;
        }

        if (shouldBeSaved) tweetCollection.save(tweet);
        return shouldBeSaved;
    }

    public static void remove(Tweet tweet) {
        tweetCollection.remove(tweet.getId());
    }
}