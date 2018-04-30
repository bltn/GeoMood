import models.Tweet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import twitter4j.GeoLocation;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TweetRepositoryTest {

    private Tweet upperCaseTweet;
    private Tweet lowerCaseTweet;
    private TweetRepository tweetRepo;

    @Before
    public void setUp() {
        tweetRepo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.TEST);

        tweetRepo.removeAll();

        upperCaseTweet = new Tweet();
        upperCaseTweet.setText("CASE CASE");
        upperCaseTweet.setUserLocation("Manchester, UK");
        upperCaseTweet.setGeoLocation(new GeoLocation(53.4808, -2.2426));

        lowerCaseTweet = new Tweet();
        lowerCaseTweet.setText("case case");
        lowerCaseTweet.setUserLocation("Manchester, UK");
        lowerCaseTweet.setGeoLocation(new GeoLocation(53.4808, -2.2426));
    }

    @Test
    public void saveValidTweet() {
        long before = tweetRepo.getCount();
        tweetRepo.save(lowerCaseTweet);
        assert(tweetRepo.getCount() == (before + 1));
    }

    @Test
    public void saveWithNoGeoLocation() {
        long before = tweetRepo.getCount();
        lowerCaseTweet.setGeoLocation(null);
        assertEquals(tweetRepo.getCount(), before);
    }

    @Test
    public void findTweetWithExistentTopic() {
        tweetRepo.save(lowerCaseTweet);
        tweetRepo.save(upperCaseTweet);

        List<Tweet> matchingTweets = tweetRepo.findTweetsWithTopic("case");
        assert(matchingTweets.size() == 2);
        assert(matchingTweets.get(0).getText().equals(lowerCaseTweet.getText()));
    }

    @Test
    public void findTweetWithNonExistentTopic() {
        tweetRepo.save(lowerCaseTweet);
        tweetRepo.save(upperCaseTweet);

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

    @Test
    public void findUSCanadaTweets() {
        createAndSaveUSCanadaTweets(4);
        assertEquals(4, tweetRepo.findUSCanadaTweetsWithTopic("sample topic").size());
    }

    @Test
    public void findEUTweets() {
        createAndSaveEUTweets(4);
        assertEquals(4, tweetRepo.findEUTweetsWithTopic("sample topic").size());
    }

    @Test
    public void findUKTweets() {
        // both tweets have their coordinates set to Manchester UK
        tweetRepo.save(lowerCaseTweet);
        tweetRepo.save(upperCaseTweet);
        assertEquals(2, tweetRepo.findUKTweetsWithTopic("case case").size());
    }

    private void createAndSaveEUTweets(int numTweets) {
        double parisLat = 48.8566;
        double parisLng = 2.3522;

        for (int i = 0; i < numTweets; i++) {
            Tweet tweet = new Tweet();
            tweet.setText("sample topic " + i);
            tweet.setGeoLocation(new GeoLocation(parisLat, parisLng));
            tweetRepo.save(tweet);
        }
    }

    private void createAndSaveUSCanadaTweets(int numTweets) {
        double sanFranciscoLat = 37.773972;
        double sanFranciscoLng = -122.431297;

        for (int i = 0; i < numTweets; i++) {
            Tweet tweet = new Tweet();
            tweet.setText("sample topic " + i);
            tweet.setGeoLocation(new GeoLocation(sanFranciscoLat, sanFranciscoLng));
            tweetRepo.save(tweet);
        }
    }
}
