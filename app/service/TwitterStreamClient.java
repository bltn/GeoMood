package service;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import play.Configuration;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterStreamClient {

    public void consumeTwitterStream(Configuration authenticationConf) throws TwitterException {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(authenticationConf.getString("CONSUMER_KEY"))
                .setOAuthConsumerSecret(authenticationConf.getString("CONSUMER_SECRET"))
                .setOAuthAccessToken(authenticationConf.getString("ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(authenticationConf.getString("ACCESS_SECRET"));

        TwitterStreamFactory tf = new TwitterStreamFactory(cb.build());
        TwitterStream twitterStream = tf.getInstance();

        StatusListener listener = new StatusListener() {

            @Override
            public void onStatus(Status status) {
                System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText());
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            @Override
            public void onScrubGeo(long userId, long upToStatusId) {
                System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            @Override
            public void onStallWarning(StallWarning warning) {
                System.out.println("Got stall warning:" + warning);
            }

            @Override
            public void onException(Exception ex) {
                ex.printStackTrace();
            }
        };

        twitterStream.addListener(listener);
        twitterStream.sample();
    }
}
