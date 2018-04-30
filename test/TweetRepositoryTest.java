import models.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import twitter4j.GeoLocation;

import java.util.List;

import static org.junit.Assert.assertFalse;

public class TweetRepositoryTest {

    private Tweet brexitTweet;
    private Tweet collegeTweet;
    private Tweet upperCaseTweet;
    private Tweet lowerCaseTweet;
    private TweetRepository tweetRepo;

    @Before
    public void setUp() {
        tweetRepo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.TEST);

        tweetRepo.removeAll();

        brexitTweet = new Tweet();
        brexitTweet.setText("brexit was a very bad idea");
        brexitTweet.setUserLocation("Manchester, UK");
        brexitTweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));

        collegeTweet = new Tweet();
        collegeTweet.setText("this tweet talks about college ok");
        collegeTweet.setUserLocation("Manchester, UK");
        collegeTweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));

        upperCaseTweet = new Tweet();
        upperCaseTweet.setText("CASE CASE");
        upperCaseTweet.setUserLocation("Manchester, UK");
        upperCaseTweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));

        lowerCaseTweet = new Tweet();
        lowerCaseTweet.setText("case case");
        lowerCaseTweet.setUserLocation("Manchester, UK");
        lowerCaseTweet.setGeoLocation(new GeoLocation(53.4808, 2.2426));
    }

    @Test
    public void saveValidTweet() {
        assert(tweetRepo.save(brexitTweet));
    }

    @Test
    public void saveWithNoGeoLocation() {
        brexitTweet.setGeoLocation(null);
        assertFalse(tweetRepo.save(brexitTweet));
    }

    @Test
    public void saveWithNoUserLocation() {
        brexitTweet.setUserLocation(null);
        assert(tweetRepo.save(brexitTweet));
    }

    @Test
    public void saveWithNoUserLocationOrGeoLocation() {
        brexitTweet.setGeoLocation(null);
        brexitTweet.setUserLocation(null);
        assertFalse(tweetRepo.save(brexitTweet));
        brexitTweet.setUserLocation("");
        assertFalse(tweetRepo.save(brexitTweet));
        brexitTweet.setUserLocation("   ");
        assertFalse(tweetRepo.save(brexitTweet));
    }

    @Test
    public void findTweetWithExistentTopic() {
        tweetRepo.save(collegeTweet);
        tweetRepo.save(brexitTweet);

        List<Tweet> matchingTweets = tweetRepo.findTweetsWithTopic("college");
        assert(matchingTweets.size() == 1);
        assert(matchingTweets.get(0).getText().equals(collegeTweet.getText()));

        matchingTweets = tweetRepo.findTweetsWithTopic("brexit");
        assert(matchingTweets.size() == 1);
        assert(matchingTweets.get(0).getText().equals(brexitTweet.getText()));
    }

    @Test
    public void findTweetWithNonExistentTopic() {
        tweetRepo.save(collegeTweet);
        tweetRepo.save(brexitTweet);

        List<Tweet> matchingTweets = tweetRepo.findTweetsWithTopic("i dont exist lalalalaalalala");
        assert(matchingTweets.size() == 0);
    }

    @Test
    public void findsCaseInsensitiveMatches() {
        tweetRepo.save(lowerCaseTweet);
        tweetRepo.save(upperCaseTweet);

        List<Tweet> caseInsensitiveMatches = tweetRepo.findTweetsWithTopic("cAsE CaSe");
        assert(caseInsensitiveMatches.size() == 2);
    }
}
