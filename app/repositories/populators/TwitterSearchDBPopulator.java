package repositories.populators;

import models.Tweet;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

import static models.TweetFactory.createFromStatusIfHasLocation;

public class TweetSearchPopulator {

    private static TweetRepository repo;
    private static Twitter searchAPI;

    private static void initStaticFields() {
        TwitterFactory tf = new TwitterFactory();
        searchAPI = tf.getInstance();
        repo = TweetRepositoryFactory.getTweetRepository("dev");
    }

    public static void main(String[] args) {
        initStaticFields();

        int requiredTweets = Integer.parseInt(args[0]);
        String topic = args[1];

        populate(requiredTweets, topic);
    }


    public static void populate(int requiredTweets, String topic) {
        int savedCount = 0;
        try {
            List<Status> statuses = getTweetsFromAPI(requiredTweets, new Query(topic));

            for (Status status: statuses) {
                Tweet tweet = createFromStatusIfHasLocation(status);
                if (tweet != null) {
                    repo.save(tweet);
                    savedCount++;
                }
            }
            System.out.println(savedCount+"/"+requiredTweets+" tweets saved.");
        } catch (TwitterException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }

    }

    private static List<Status> getTweetsFromAPI(int requiredTweets, Query query) throws TwitterException {
        // The bottleneck for populating the DB is Google's GeoCoding API, which has a rate limit of 2,500
        if (requiredTweets > 2500) requiredTweets = 2500;

        // Throw TwitterException if the rate limit has already been exceeded
        int rateLimitRemaining = searchAPI.getRateLimitStatus().get("/search/tweets").getRemaining();
        if (rateLimitRemaining == 0) {
            int resetCountDown = searchAPI.getRateLimitStatus().get("/search/tweets").getSecondsUntilReset();
            throw new TwitterException("RESTful API rate limit (180/15 minutes) exceeded. "+resetCountDown+"s left.");
        }

        List<Status> statuses = new ArrayList<Status>();
        query.setCount(100);

        while (statuses.size() < requiredTweets && rateLimitRemaining > 0) {
            QueryResult result = searchAPI.search(query);
            statuses.addAll(result.getTweets());

            long lowestStatusId = Long.MAX_VALUE;

            for (Status status : statuses) {
                lowestStatusId = Math.min(status.getId(), lowestStatusId);
            }

            query.setMaxId(lowestStatusId - 1);
            rateLimitRemaining = searchAPI.getRateLimitStatus().get("/search/tweets").getRemaining();
        }

        return statuses;
    }
}

