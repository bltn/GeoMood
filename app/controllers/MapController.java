package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import views.html.map_visualisation;

import java.util.List;

public class MapController extends Controller {

    public Result visualiseTopic() {
        String topic = request().getQueryString("topic");
        TweetRepository repo = TweetRepositoryFactory.getTweetRepository("dev");
        List<Tweet> sampleTweets = repo.findTweetsWithTopic(topic);
        return ok(map_visualisation.render(sampleTweets));
    }
}