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

import java.util.List;
import java.util.Map;

public class StatsController extends Controller {

    public Result visualiseTopic() {

        String topic = request().getQueryString("topic");
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.PRODUCTION);

        List<Tweet> tweets = repo.findTweetsWithTopic(topic);
        Map<String, Integer> sentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        if (tweets.size() > 0) {
            return ok(stats_visualisation.render(tweets, topic, sentimentFrequencies));
        } else {
            return ok(tweets_not_found.render(topic));
        }
    }
}
