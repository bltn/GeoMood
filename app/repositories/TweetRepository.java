package repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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

    public static List<Tweet> findTweetsWithTopic(String topic) {
        MongoCursor<Tweet> tweetsContainingTopic = tweetCollection.find("{text:#}", Pattern.compile(".*"+topic+".*")).as(Tweet.class);
        return convertMongoCursorToList(tweetsContainingTopic);
    }

    public static void remove(Tweet tweet) {
        tweetCollection.remove(tweet.getId());
    }

    private static List<Tweet> convertMongoCursorToList(MongoCursor<Tweet> mongoCursorTweets) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        while (mongoCursorTweets.hasNext()) {
            tweets.add(mongoCursorTweets.next());
        }
        return tweets;
    }
}