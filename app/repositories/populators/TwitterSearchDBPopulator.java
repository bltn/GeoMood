package repositories.populators;

import models.Tweet;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

import static models.TweetFactory.createFromStatus;

public class TwitterSearchDBPopulator {

    private static TweetRepository repo;
    private static Twitter searchAPI;

    private static void initStaticFields() {
        TwitterFactory tf = new TwitterFactory();
        searchAPI = tf.getInstance();
        repo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.PRODUCTION);
    }

    public static void main(String[] args) {
        initStaticFields();

        int requiredTweets = Integer.parseInt(args[0]);
        String topic = args[1];

        populate(requiredTweets, topic);
    }


    public static void populate(int requiredTweets, String topic) {
        long initialDBEntryCount = repo.getCount();
        List<Status> statuses = getTweetsFromAPI(requiredTweets, new Query(topic));
        System.out.println(statuses.size() + " tweets fetched");

        long startingDBSize = repo.getCount();
        int iterationCounter = 0;

        for (Status status: statuses) {
            Tweet tweet = createFromStatus(status);
            if (tweet != null) {
                repo.save(tweet);
            }
            iterationCounter++;
            System.out.println("Tweets saved so far: " + (repo.getCount() - startingDBSize)+"/"+iterationCounter);
        }
        long entriesSaved = repo.getCount() - initialDBEntryCount;
        System.out.println("You requested "+requiredTweets+" tweets, and "+entriesSaved+" were saved");
    }

    private static List<Status> getTweetsFromAPI(int requiredTweets, Query query) {
        List<Status> statuses = new ArrayList<Status>();

        try {
            // The bottleneck for populating the DB is Google's GeoCoding API, which has a rate limit of 2,500/day
            if (requiredTweets > 2500) requiredTweets = 2500;

            int querySize = 100;
            if (requiredTweets < querySize) querySize = requiredTweets;

            query.setCount(querySize);

            while (statuses.size() < requiredTweets) {
                // will throw TwitterException when the rate limit is reached
                QueryResult result = searchAPI.search(query);

                if (result.getTweets().size() == 0) {
                    System.out.println("Stopped result iteration early: API calls weren't returning any more tweets");
                    break;
                }

                statuses.addAll(result.getTweets());

                long lowestStatusId = Long.MAX_VALUE;

                for (Status status : statuses) {
                    lowestStatusId = Math.min(status.getId(), lowestStatusId);
                }

                query.setMaxId(lowestStatusId - 1);
            }
        } catch (TwitterException e) {
            System.out.println(e.getMessage());
        }
        return statuses;
    }
}

