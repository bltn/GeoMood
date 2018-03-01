import models.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repositories.TweetRepository;
import twitter4j.GeoLocation;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class TweetRepositoryTest {

    private Tweet brexitTweet;
    private Tweet collegeTweet;

    @Before
    public void setUp() {
        brexitTweet = new Tweet();
        brexitTweet.setText("brexit was a very bad idea");
        brexitTweet.setUserLocation("Manchester, UK");
        brexitTweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));

        collegeTweet = new Tweet();
        collegeTweet.setText("this tweet talks about college ok");
        collegeTweet = new Tweet();
        collegeTweet.setText("this tweet talks about college");
    }

    @After
    public void tearDown() {
        TweetRepository.remove(brexitTweet);
    }

    @Test
    public void saveValidTweet() {
        assert(TweetRepository.save(brexitTweet));
    }

    @Test
    public void saveWithNoGeoLocation() {
        brexitTweet.setGeoLocation(null);
        assert(TweetRepository.save(brexitTweet));
    }

    @Test
    public void saveWithNoUserLocation() {
        brexitTweet.setUserLocation(null);
        assert(TweetRepository.save(brexitTweet));
    }

    @Test
    public void saveWithNoUserLocationOrGeoLocation() {
        brexitTweet.setGeoLocation(null);
        brexitTweet.setUserLocation(null);
        assertFalse(TweetRepository.save(brexitTweet));
        brexitTweet.setUserLocation("");
        assertFalse(TweetRepository.save(brexitTweet));
        brexitTweet.setUserLocation("   ");
        assertFalse(TweetRepository.save(brexitTweet));
    }

    @Test
    public void findTweetWithExistingTopic() {
        TweetRepository.save(collegeTweet);
        TweetRepository.save(brexitTweet);

        List<Tweet> matchingTweets = TweetRepository.findTweetsWithTopic(collegeTweet.getText());
        assert(matchingTweets.size() == 1);
    }
}
