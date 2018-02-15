import org.junit.BeforeClass;
import org.junit.Test;
import play.Application;
import play.Configuration;
import play.api.Play;
import play.test.WithApplication;
import service.TwitterSearch;
import sun.security.krb5.Config;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.inject.Inject;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static play.test.Helpers.fakeApplication;

public class TwitterSearchTest extends WithApplication {

    // all normal, make sure you get some tweets
    // incorrect config info
    // no topic entered
    // topic too long entered

    private Application fakeApp;

    @BeforeClass
    public void setUp() {
        fakeApp = fakeApplication();
    }

    @Test
    public void normalRequest() {
        Configuration conf = fakeApp.injector().instanceOf(Configuration.class);

        TwitterSearch searchAPI = new TwitterSearch(conf);
        // 15 tweets should be returned and the given topic should be mentioned at least once
        try {
            List<Status> result = searchAPI.getTweets(new Query("football"));
            assertEquals(result.size(), 15);
            // convert to lowercase to avoid case mismatches
            assertTrue(result.get(0).getText().toLowerCase().contains("football"));
        } catch (TwitterException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void noSearchTopic() {

    }
}
