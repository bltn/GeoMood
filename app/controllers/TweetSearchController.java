package controllers;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import models.Tweet;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import service.TwitterSearch;
import play.*;
import play.mvc.*;

import service.NLP;
import twitter4j.Query;
import twitter4j.Status;
import twitter4j.TwitterException;
import views.html.*;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;

public class TweetSearchController extends Controller {

    @Inject
    Configuration conf;

    public Result renderSearchForm() {
        return ok(tweet_search.render());
    }

    public Result search() {
        // Fetch the topic name
        String topic = request().getQueryString("topic");
        try {
            TwitterSearch tweetSearch = new TwitterSearch(conf);
            // Fetch tweets from the API
            List<Status> tweets = tweetSearch.getTweets(new Query(topic));
            // Assign each tweet a sentiment value
            Map<String, Integer> sentimentMap = NLP.getSentimentMapping(tweets);
            // Render sentiment-rated tweet listing
            return ok(show_tweets.render(sentimentMap));
        } catch (TwitterException e) {
            // Render internal server error page showing error msg
            return internalServerError(e.getMessage());
        }
    }
}
