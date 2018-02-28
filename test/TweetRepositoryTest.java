import models.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repositories.TweetRepository;
import twitter4j.GeoLocation;

import static org.junit.Assert.assertFalse;

public class TweetRepositoryTest {

    private Tweet tweet;

    @Before
    public void setUp() {
        tweet = new Tweet();

        tweet.setText("brexit was a very bad idea");
        tweet.setUserLocation("Manchester, UK");
        tweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));
    }

    @After
    public void tearDown() {
        TweetRepository.remove(tweet);
    }

    @Test
    public void saveValidTweet() {
        assert(TweetRepository.save(tweet));
    }

    @Test
    public void saveWithNoGeoLocation() {
        tweet.setGeoLocation(null);
        assert(TweetRepository.save(tweet));
    }

    @Test
    public void saveWithNoUserLocation() {
        tweet.setUserLocation(null);
        assert(TweetRepository.save(tweet));
    }

    @Test
    public void saveWithNoUserLocationOrGeoLocation() {
        tweet.setGeoLocation(null);
        tweet.setUserLocation(null);
        assertFalse(TweetRepository.save(tweet));
        tweet.setUserLocation("");
        assertFalse(TweetRepository.save(tweet));
        tweet.setUserLocation("   ");
        assertFalse(TweetRepository.save(tweet));
    }
}
