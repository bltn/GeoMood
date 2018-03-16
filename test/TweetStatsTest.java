import models.Tweet;
import org.junit.Before;
import org.junit.Test;
import service.TweetStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TweetStatsTest {
    private Map<String, Integer> sentimentFrequencies;
    private List<Tweet> tweets;

    @Before
    public void setUp() {
        tweets = new ArrayList<Tweet>();
    }

    @Test
    public void getTweetSentimentFrequency() {
        // Populate the list with 30 tweets
        for (int i = 0; i < 30; i++) tweets.add(new Tweet());
        // give 15 tweets a positive sentiment value
        for (int i = 0; i < 15; i++) tweets.get(i).setSentimentValue(3);
        // give 10 tweets a neutral sentiment value
        for (int i = 15; i < 25; i++) tweets.get(i).setSentimentValue(2);
        // give 5 tweets a negative sentiment value
        for (int i = 25; i < 20; i++) tweets.get(i).setSentimentValue(1);

        sentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        assertEquals((Integer)15, sentimentFrequencies.get("positive"));
        assertEquals((Integer)10, sentimentFrequencies.get("neutral"));
        assertEquals((Integer)5, sentimentFrequencies.get("negative"));
    }
}
