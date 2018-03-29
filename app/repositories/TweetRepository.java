package repositories;

import models.Tweet;
import org.jongo.MongoCursor;
import service.geo.BoundingBox;
import service.geo.EUBoundingBox;
import service.geo.UKBoundingBox;
import service.geo.USCanadaBoundingBox;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TweetRepository extends Repository {


    public TweetRepository(String dbName, String collectionName) {
        super(dbName, collectionName);
    }

    public boolean save(Tweet t) {
        if (t.getGeoLocation() != null) {
            collection.save(t);
            return true;
        }
        return false;
    }

    public void remove(Tweet t) {
        collection.remove(t.getId());
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

    public List<Tweet> findUSCanadaTweetsWithTopic(String topic) {
        MongoCursor<Tweet> tweets = fetchByTopic(topic);
        USCanadaBoundingBox box = new USCanadaBoundingBox();

        return filterMongoCursorByBoundingBox(tweets, box);
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
        MongoCursor<Tweet> matchingTweets = collection.find("{text:#}", Pattern.compile(".*"+topic+".*")).as(Tweet.class);
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