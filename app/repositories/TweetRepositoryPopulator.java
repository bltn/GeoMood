package repositories;

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
 * While running, populates the given database collection with tweets from the Twitter Streaming API
 */
public class TweetRepositoryPopulator {

    /**
     * @param args 1 argument, as follows:
     *             tweet limit (how many tweets to save before exiting)
     */
    public static void main(String[] args) {
        int tweetSaveLimit = Integer.parseInt(args[0]);

        TwitterStreamFactory tf = new TwitterStreamFactory();
        TwitterStream twitterStream = tf.getInstance();

        StatusListener listener = new StatusListener() {

            int tweetsSavedSoFar = 0;

            @Override
            public void onStatus(Status status) {
                if (tweetsSavedSoFar >= tweetSaveLimit) System.exit(0);

                if (status.getLang().equals("en")) {
                    Tweet tweet = new Tweet();
                    tweet.setText(status.getText());
                    tweet.setGeoLocation(status.getGeoLocation());
                    tweet.setUserLocation(status.getUser().getLocation());

                    TweetRepository.save(tweet);
                    tweetsSavedSoFar++;

                    System.out.println("**      " + tweetsSavedSoFar + " tweets saved       **");
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
