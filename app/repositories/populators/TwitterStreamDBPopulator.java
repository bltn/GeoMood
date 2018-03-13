package repositories.populators;

import com.google.maps.model.LatLng;
import models.Tweet;
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
                // whether the tweet is valid and should be saved
                boolean isValidTweet = false;

                if (tweetsSavedSoFar >= tweetSaveLimit) System.exit(0);

                boolean hasAtLeastOneLocation = status.getGeoLocation() != null || status.getUser().getLocation() != null;

                if (status.getLang().equals("en") && hasAtLeastOneLocation) {
                    Tweet tweet = new Tweet();
                    tweet.setText(status.getText());
                    tweet.setTweetId(status.getId());

                    // infer GeoLocation if it isn't explicitly set
                    if (status.getGeoLocation() == null) {
                        LatLng coordinates = LocationTranslator.addressToCoordinates(status.getUser().getLocation());
                        // only save if coordinates were derived from the user profile location
                        if (coordinates != null) {
                            // set geolocation & textual location based on user profile
                            tweet.setGeoLocation(new GeoLocation(coordinates.lat, coordinates.lng));
                            tweet.setUserLocation(status.getUser().getLocation());
                            // tweet is valid if it has a geolocation
                            isValidTweet = true;
                        }
                    } else {
                        // set geolocation & textual location
                        tweet.setGeoLocation(status.getGeoLocation());
                        tweet.setUserLocation(status.getUser().getLocation());
                        // tweet is valid if it has a geolocation
                        isValidTweet = true;
                    }

                    if (isValidTweet) {
                        // assign a sentiment value
                        tweet.setSentimentValue(NLP.getSentiment(status.getText()));

                        if (tweetRepo.save(tweet)) tweetsSavedSoFar++;
                    }
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
