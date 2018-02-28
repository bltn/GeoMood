package service;

import play.Configuration;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import javax.inject.Inject;
import java.util.List;

public class TwitterSearch {

    private Twitter twitterAPI;

    public TwitterSearch(Configuration conf) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(conf.getString("CONSUMER_KEY"))
                .setOAuthConsumerSecret(conf.getString("CONSUMER_SECRET"))
                .setOAuthAccessToken(conf.getString("ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(conf.getString("ACCESS_SECRET"));

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitterAPI = tf.getInstance();
    }

    public List<Status> getTweets(Query topic) throws TwitterException {
        topic.setCount(15);
        QueryResult result = twitterAPI.search(topic);
        List<Status> tweets = result.getTweets();

        for (int i = 0; i < 10; i++)
            result = twitterAPI.search(topic);
            tweets.addAll(result.getTweets());

        return tweets;
    }
}