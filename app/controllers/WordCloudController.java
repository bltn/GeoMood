package controllers;

import models.Tweet;
import play.mvc.Controller;
import play.mvc.Result;
import repositories.DBEnvironment;
import repositories.TweetRepository;
import repositories.TweetRepositoryFactory;
import service.MapUtil;
import service.WordCounter;
import views.html.wordcloud;

import java.util.List;
import java.util.Map;

public class WordCloudController extends Controller {

    public Result visualiseTopic() {
        String topic = request().getQueryString("topic");

        TweetRepository repo = TweetRepositoryFactory.getTweetRepository(DBEnvironment.PRODUCTION);
        List<Tweet> tweets = repo.findTweetsWithTopic(topic);

        Map<String, Integer> wordOccurences = WordCounter.getWordCounts(tweets);
        Map<String, Integer> top50WordOccurences = MapUtil.getTop50(wordOccurences);

        return ok(wordcloud.render(topic, top50WordOccurences));
    }
}