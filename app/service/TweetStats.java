package service;

import models.Tweet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetStats {

    public static Map<String, Integer> getSentimentFrequency(List<Tweet> tweets) {
        Map<String, Integer> sentimentFrequencies = getBlankSentimentFrequencyMap();
        calculateSentimentFrequencies(sentimentFrequencies, tweets);
        return sentimentFrequencies;
    }

    private static void calculateSentimentFrequencies(Map<String, Integer> sentimentFrequencies, List<Tweet> tweets) {
        for (Tweet tweet : tweets) {
            if (tweet.getSentimentValue() > 2) {
                incrementSentimentFrequency(sentimentFrequencies, "positive");
            } else if (tweet.getSentimentValue() == 2) {
                incrementSentimentFrequency(sentimentFrequencies, "neutral");
            } else {
                incrementSentimentFrequency(sentimentFrequencies, "negative");
            }
        }
    }

    private static Map<String, Integer> getBlankSentimentFrequencyMap() {
        Map<String, Integer> sentimentFrequency = new HashMap<String, Integer>();

        sentimentFrequency.put("positive", 0);
        sentimentFrequency.put("neutral", 0);
        sentimentFrequency.put("negative", 0);

        return sentimentFrequency;
    }

    private static void incrementSentimentFrequency(Map<String, Integer> map, String sentiment) {
        Integer currentSentimentCount = map.get(sentiment);
        Integer newSentimentCount = currentSentimentCount + 1;
        map.put(sentiment, newSentimentCount);
    }

}
