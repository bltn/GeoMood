package repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import service.geo.BoundingBox;
import service.geo.EUBoundingBox;
import service.geo.UKBoundingBox;

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
        return convertMongoCursorToList(fetchByTopic(topic));
    }

    public List<Tweet> findUKTweetsWithTopic(String topic) {
        MongoCursor<Tweet> tweets = fetchByTopic(topic);
        UKBoundingBox box = new UKBoundingBox();

        return filterMongoCursorByBoundingBox(tweets, box);
    }

    public List<Tweet> findEUTweetsWithTopic(String topic) {
        MongoCursor<Tweet> tweets = fetchByTopic(topic);
        EUBoundingBox box = new EUBoundingBox();

        return filterMongoCursorByBoundingBox(tweets, box);
    }

    public void remove(Tweet tweet) {
        tweetCollection.remove(tweet.getId());
    }

    private List<Tweet> filterMongoCursorByBoundingBox(MongoCursor<Tweet> cursor, BoundingBox boundingBox) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        while (cursor.hasNext()) {
            Tweet tweet = cursor.next();
            if (boundingBox.contains(tweet.getGeoLocation().getLatitude(), tweet.getGeoLocation().getLongitude())) {
                tweets.add(tweet);
            }
        }
        return tweets;
    }

    private MongoCursor<Tweet> fetchByTopic(String topic) {
        MongoCursor<Tweet> matchingTweets = tweetCollection.find("{text:#}", Pattern.compile(".*"+topic+".*")).as(Tweet.class);
        return matchingTweets;
    }

    private List<Tweet> convertMongoCursorToList(MongoCursor<Tweet> cursor) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        while (cursor.hasNext()) {
            tweets.add(cursor.next());
        }
        return tweets;
    }
}