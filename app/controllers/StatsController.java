package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import service.TweetStats;
import views.html.stats_visualisation;
import views.html.tweets_not_found;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsController extends Controller {

    private String topic;
    private Map<String, Map<String, Double>> allSentimentFrequencies;
    private Map<String, Map<String, Double>> allSentimentPercentages;

    public Result visualiseAllTopicCategories() {
        topic = request().getQueryString("topic");

        List<Tweet> allTweets = fetchAllTweets(topic, DBEnvironment.PRODUCTION);
        List<Tweet> ukTweets = fetchUKTweets(topic, DBEnvironment.PRODUCTION);
        List<Tweet> euTweets = fetchEUTweets(topic, DBEnvironment.PRODUCTION);
        List<Tweet> usCanadaTweets = fetchUSCanadaTweets(topic, DBEnvironment.PRODUCTION);

        allSentimentFrequencies = new HashMap<String, Map<String, Double>>();
        allSentimentPercentages = new HashMap<String, Map<String, Double>>();

        allSentimentFrequencies.put("all", TweetStats.getSentimentFrequency(allTweets));
        allSentimentFrequencies.put("UK", TweetStats.getSentimentFrequency(ukTweets));
        allSentimentFrequencies.put("EU", TweetStats.getSentimentFrequency(euTweets));
        allSentimentFrequencies.put("USCAN", TweetStats.getSentimentFrequency(usCanadaTweets));

        allSentimentPercentages.put("UK", TweetStats.getSentimentPercentages(ukTweets));
        allSentimentPercentages.put("EU", TweetStats.getSentimentPercentages(euTweets));
        allSentimentPercentages.put("USCAN", TweetStats.getSentimentPercentages(usCanadaTweets));

        return renderAppropriatePage(allTweets);
    }

    private List<Tweet> fetchAllTweets(String topic, DBEnvironment dbEnvironment) {
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(dbEnvironment);
        return repo.findTweetsWithTopic(topic);
    }

    private List<Tweet> fetchEUTweets(String topic, DBEnvironment dbEnvironment) {
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(dbEnvironment);
        return repo.findEUTweetsWithTopic(topic);
    }

    private List<Tweet> fetchUKTweets(String topic, DBEnvironment dbEnvironment) {
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(dbEnvironment);
        return repo.findUKTweetsWithTopic(topic);
    }

    private List<Tweet> fetchUSCanadaTweets(String topic, DBEnvironment dbEnvironment) {
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(dbEnvironment);
        return repo.findUSCanadaTweetsWithTopic(topic);
    }

    private Result renderAppropriatePage(List<Tweet> tweets) {
        if (tweets.size() > 0) {
            return ok(stats_visualisation.render(topic, allSentimentFrequencies, allSentimentPercentages));
        } else {
            return ok(tweets_not_found.render(topic));
        }
    }
}
