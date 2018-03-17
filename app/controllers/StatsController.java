package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import service.TweetStats;
import views.html.country_stats_visualisation;
import views.html.stats_visualisation;
import views.html.tweets_not_found;

import java.util.List;
import java.util.Map;

public class StatsController extends Controller {

    public Result visualiseTopic() {
        String topic = request().getQueryString("topic");

        List<Tweet> tweets = fetchTweets(topic, DBEnvironment.PRODUCTION);
        Map<String, Integer> sentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        if (tweets.size() > 0) {
            return ok(stats_visualisation.render(tweets, topic, sentimentFrequencies));
        } else {
            return ok(tweets_not_found.render(topic));
        }
    }

    public Result visualiseTopicByCountry() {
        String topic = request().getQueryString("topic");

        List<Tweet> tweets = fetchTweets(topic, DBEnvironment.PRODUCTION);
        Map<String, Integer> sentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        if (tweets.size() > 0) {
            return ok(country_stats_visualisation.render(topic, sentimentFrequencies));
        } else {
            return ok(tweets_not_found.render(topic));
        }
    }

    private List<Tweet> fetchTweets(String topic, DBEnvironment dbEnvironment) {
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(dbEnvironment);
        return repo.findTweetsWithTopic(topic);
    }
}
