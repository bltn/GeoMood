package repositories.populators;

import com.google.maps.model.LatLng;
import models.Tweet;
import models.TweetFactory;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import service.LocationTranslator;
import service.NLP;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;


/**
 * While running, populates the given database collection with tweets from the Twitter Streaming API
 */
public class TwitterStreamDBPopulator {

    /**
     * @param args 1 argument, as follows:
     *             tweet limit (how many tweets to save before exiting)
     */
    public static void main(String[] args) {
        int tweetSaveLimit = Integer.parseInt(args[0]);


        TwitterStreamFactory tf = new TwitterStreamFactory();
        TwitterStream twitterStream = tf.getInstance();

        TweetRepository tweetRepo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.TEST);

        StatusListener listener = new StatusListener() {

            int tweetsSavedSoFar = 0;

            @Override
            public void onStatus(Status status) {
                if (tweetsSavedSoFar >= tweetSaveLimit) System.exit(0);
                Tweet tweet = TweetFactory.createFromStatus(status);
                // non-null implies it's valid as determined by the Factory
                if (tweet != null) {
                    if (tweetRepo.save(tweet)) tweetsSavedSoFar++;
                }
                System.out.println("**      " + tweetsSavedSoFar + " tweets saved       **");
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
