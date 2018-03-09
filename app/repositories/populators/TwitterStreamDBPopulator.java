package repositories.populators;

import com.google.maps.model.LatLng;
import models.Tweet;
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
public class TweetRepositoryPopulator {

    private static List<Status> tweets = new ArrayList<Status>();

    /**
     * @param args 1 argument, as follows:
     *             tweet limit (how many tweets to save before exiting)
     */
    public static void main(String[] args) {
        int tweetSaveLimit = Integer.parseInt(args[0]);

        TwitterStreamFactory tf = new TwitterStreamFactory();
        TwitterStream twitterStream = tf.getInstance();

        TweetRepository tweetRepo = TweetRepositoryFactory.getTweetRepository("dev");

        StatusListener listener = new StatusListener() {

            int tweetsSavedSoFar = 0;

            @Override
            public void onStatus(Status status) {
                tweets.add(status);
                if (tweetsSavedSoFar >= tweetSaveLimit) System.exit(0);

                boolean hasAtLeastOneLocation = status.getGeoLocation() != null || status.getUser().getLocation() != null;

                if (status.getLang().equals("en") && hasAtLeastOneLocation) {
                    Tweet tweet = new Tweet();
                    tweet.setText(status.getText());

                    // infer GeoLocation if it isn't explicitly set
                    if (status.getGeoLocation() == null) {
                        LatLng coordinates = LocationTranslator.addressToCoordinates(status.getUser().getLocation());
                        tweet.setGeoLocation(new GeoLocation(coordinates.lat, coordinates.lng));
                        tweet.setUserLocation(status.getUser().getLocation());
                    } else {
                        tweet.setGeoLocation(status.getGeoLocation());
                        tweet.setUserLocation(status.getUser().getLocation());
                    }

                    // assign a sentiment value
                    tweet.setSentimentValue(NLP.getSentiment(status.getText()));

                    tweetRepo.save(tweet);
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
