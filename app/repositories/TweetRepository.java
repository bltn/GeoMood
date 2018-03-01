package repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.typesafe.config.ConfigFactory;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TweetRepository {

    private MongoCollection tweetCollection;

    public TweetRepository(String databaseName, String collectionName) {
        tweetCollection = loadMongoCollection(databaseName, collectionName);
    }

    private MongoCollection loadMongoCollection(String databaseName, String collectionName) {
        DB database = new MongoClient().getDB(databaseName);
        Jongo jongoClient = new Jongo(database);
        return jongoClient.getCollection(collectionName);
    }

    public boolean save(Tweet tweet) {
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

    public List<Tweet> findTweetsWithTopic(String topic) {
        MongoCursor<Tweet> tweetsContainingTopic = tweetCollection.find("{text:#}", Pattern.compile(".*"+topic+".*")).as(Tweet.class);
        return convertMongoCursorToList(tweetsContainingTopic);
    }

    public void remove(Tweet tweet) {
        tweetCollection.remove(tweet.getId());
    }

    private List<Tweet> convertMongoCursorToList(MongoCursor<Tweet> mongoCursorTweets) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        while (mongoCursorTweets.hasNext()) {
            tweets.add(mongoCursorTweets.next());
        }
        return tweets;
    }
}