package service;

import twitter4j.*;

import java.util.List;

public class TwitterSearch {

    private Twitter twitterAPI;

    public TwitterSearch() {
        TwitterFactory tf = new TwitterFactory();
        twitterAPI = tf.getInstance();
    }

    public List<Status> getTweets(Query query) throws TwitterException {
        // The API's rate limit is 180 calls every 15 minutes
        int rateLimitRemaining = twitterAPI.getRateLimitStatus().get("/search/tweets").getRemaining();
        // Throw exception if the rate limit has been hit
        if (rateLimitRemaining == 0) throw new TwitterException("Twitter's REST API rate limit (180 calls per 15 minutes) exceeded.");

        // Return 100 tweets per result page
        query.setCount(100);

        QueryResult result = twitterAPI.search(query);
        return result.getTweets();
    }
}