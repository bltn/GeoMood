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

    public long getCount() {
        return tweetCollection.count();
    }

    public boolean save(Tweet tweet) {
        try {
            // save if it has a geolocation
            if (tweet.getGeoLocation() != null) {
                tweetCollection.save(tweet);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error saving tweet: " + e.getMessage());
            return false;
        }
    }

    public List<Tweet> findTweetsWithTopic(String topic) {
        return fetchByTopic(topic);
    }

    public void remove(Tweet tweet) {
        tweetCollection.remove(tweet.getId());
    }

    private List<Tweet> fetchByTopic(String topic) {
        MongoCursor<Tweet> matchingTweets = tweetCollection.find("{text:#}", Pattern.compile(".*"+topic+".*")).as(Tweet.class);
        return convertMongoCursorToList(matchingTweets);
    }

    private List<Tweet> convertMongoCursorToList(MongoCursor<Tweet> mongoCursorTweets) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        while (mongoCursorTweets.hasNext()) {
            tweets.add(mongoCursorTweets.next());
        }
        return tweets;
    }
}