import models.Tweet;
import org.junit.Before;
import org.junit.Test;
import service.TweetStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TweetStatsTest {
    private List<Tweet> tweets;

    @Before
    public void setUp() {
        tweets = new ArrayList<Tweet>();
        addPositiveTweets(tweets, 15);
        addNeutralTweets(tweets, 10);
        addNegativeweets(tweets, 5);
    }

    @Test
    public void getTweetSentimentPercentages() {
        Map<String, Double> sentimentPercentages = TweetStats.getSentimentPercentages(tweets);

        assertEquals(50, Math.round(sentimentPercentages.get("positive")));
        assertEquals(33, Math.round(sentimentPercentages.get("neutral")));
        assertEquals(17, Math.round(sentimentPercentages.get("negative")));
    }

    @Test
    public void getTweetSentimentFrequency() {
        Map<String, Double> sentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        assertEquals((Double)15.0, sentimentFrequencies.get("positive"));
        assertEquals((Double)10.0, sentimentFrequencies.get("neutral"));
        assertEquals((Double)5.0, sentimentFrequencies.get("negative"));
    }

    private void addPositiveTweets(List<Tweet> tweets, int numTweets) {
        for (int i = 0; i < numTweets; i++) {
            Tweet tweet = new Tweet();
            tweet.setSentimentValue(3);
            tweets.add(tweet);
        }
    }

    private void addNeutralTweets(List<Tweet> tweets, int numTweets) {
        for (int i = 0; i < numTweets; i++) {
            Tweet tweet = new Tweet();
            tweet.setSentimentValue(2);
            tweets.add(tweet);
        }
    }

    private void addNegativeweets(List<Tweet> tweets, int numTweets) {
        for (int i = 0; i < numTweets; i++) {
            Tweet tweet = new Tweet();
            tweet.setSentimentValue(1);
            tweets.add(tweet);
        }
    }
}
