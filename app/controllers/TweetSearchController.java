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
        // Search the Twitter API for the given topic
        TwitterSearch tweetSearch = new TwitterSearch(conf);
        try {
            List<Status> tweets = tweetSearch.getTweets(new Query(topic));

            Status tws = tweets.get(0);


            // Assign each tweet a sentiment value
            Map<String, Integer> sentimentMap = NLP.getSentimentMapping(tweets);


            /**DB db = new MongoClient().getDB("dbname");

            Jongo jongo = new Jongo(db);
            MongoCollection friends = jongo.getCollection("friends");

            MongoCursor<Friend> all = friends.find("{name: 'Joe'}").as(Friend.class);
            Friend one = friends.findOne("{name: 'Joe'}").as(Friend.class);


            Friend joe = new Friend("Joe", 27);

            friends.save(joe);
            joe.age = 28;
            friends.save(joe);**/



            // Render sentiment-rated tweet listing
            return ok(show_tweets.render(sentimentMap));
        } catch (TwitterException e) {
            // Render internal server error page showing error msg
            return internalServerError(e.getMessage());
        }
    }
}
