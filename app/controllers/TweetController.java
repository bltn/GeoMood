package controllers;

import models.Tweet;
import play.Configuration;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import views.html.show_tweets;
import views.html.tweet_search;

import javax.inject.Inject;
import java.util.List;

public class TweetController extends Controller {

    @Inject
    Configuration conf;

    public Result renderSearchForm() {
        return ok(tweet_search.render());
    }

    public Result search() {
        // Fetch the topic name
        String topic = request().getQueryString("topic");
        TweetRepository tweetRepo = TweetRepositoryFactory.getTweetRepository("dev");
        List<Tweet> tweetsWithTopic = tweetRepo.findTweetsWithTopic(topic);
        return ok(show_tweets.render(tweetsWithTopic));
    }
}
