package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import views.html.show_tweets;

import java.util.List;

public class TweetController extends Controller {

    public Result search() {
        // Fetch the topic name
        String topic = request().getQueryString("topic");
        TweetRepository tweetRepo = TweetRepositoryFactory.getTweetRepository("dev");
        List<Tweet> tweetsWithTopic = tweetRepo.findTweetsWithTopic(topic);
        return ok(show_tweets.render(tweetsWithTopic));
    }
}
