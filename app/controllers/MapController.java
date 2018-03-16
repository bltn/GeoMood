package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import service.TweetStats;
import views.html.map_visualisation;
import views.html.tweets_not_found;

import java.util.List;
import java.util.Map;

public class MapController extends Controller {

    public Result visualiseTopic() {
        String topic = request().getQueryString("topic");
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.PRODUCTION);

        List<Tweet> tweets = repo.findTweetsWithTopic(topic);
        //Map<String, Integer> tweetSentimentFrequencies = TweetStats.getSentimentFrequency(tweets);

        if (tweets.size() > 0) {
            return ok(map_visualisation.render(tweets, topic));
        } else {
            return ok(tweets_not_found.render(topic));
        }

    }
}