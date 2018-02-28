package service;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import play.Application;
import play.Configuration;
import play.Play;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.List;

/**
 * Populates the given database collection with tweets from the Twitter Streaming API
 *
 *
 */
public class TweetDBPopulator {

    public static void main(String[] args) {
        TwitterStreamFactory tf = new TwitterStreamFactory();
        TwitterStream twitterStream = tf.getInstance();

        DB db = new MongoClient().getDB("geomood");
        Jongo jongoClient = new Jongo(db);

        MongoCollection tweets = jongoClient.getCollection("tweets");

        StatusListener listener = new StatusListener() {

            @Override
            public void onStatus(Status status) {
                if (status.getLang().equals("en")) {
                    Tweet tweet = new Tweet();
                    tweet.setText(status.getText());
                    tweet.setGeoLocation(status.getGeoLocation());
                    tweet.setUserLocation(status.getUser().getLocation());

                    tweets.save(tweet);
                }
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {}

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {}

            @Override
            public void onStallWarning(StallWarning warning) {}

            @Override
            public void onException(Exception ex) {}
        };
        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}
