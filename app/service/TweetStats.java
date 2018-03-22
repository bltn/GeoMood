package service;

import models.Tweet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TweetStats {

    public static Map<String, Double> getSentimentFrequency(List<Tweet> tweets) {
        Map<String, Double> sentimentFrequencies = mapSentimentFrequencies(tweets);
        return sentimentFrequencies;
    }

    public static Map<String,Double> getSentimentPercentages(List<Tweet> tweets) {
        Map<String, Double> sentimentPercentages = mapSentimentPercentages(tweets);
        return sentimentPercentages;
    }

    private static Map<String,Double> mapSentimentPercentages(List<Tweet> tweets) {
        Map<String, Double> sentimentFrequencies = mapSentimentFrequencies(tweets);
        Map<String, Double> sentimentPercentages = getBlankSentimentMap();

        Double totalTweetCount = sentimentFrequencies.get("positive") + sentimentFrequencies.get("neutral")
                + sentimentFrequencies.get("negative");

        Double positivePercentage = (sentimentFrequencies.get("positive") * 100.0) / totalTweetCount;
        Double neutralPercentage = (sentimentFrequencies.get("neutral") * 100.0) / totalTweetCount;
        Double negativePercentage = (sentimentFrequencies.get("negative") * 100.0) / totalTweetCount;

        sentimentPercentages.put("positive", positivePercentage);
        sentimentPercentages.put("neutral", neutralPercentage);
        sentimentPercentages.put("negative", negativePercentage);

        return sentimentPercentages;
    }

    private static Map<String, Double> mapSentimentFrequencies(List<Tweet> tweets) {
        Map<String, Double> sentimentFrequencies = getBlankSentimentMap();

        for (Tweet tweet : tweets) {
            if (tweet.getSentimentValue() > 2) {
                incrementSentimentFrequency(sentimentFrequencies, "positive");

            } else if (tweet.getSentimentValue() == 2) {
                incrementSentimentFrequency(sentimentFrequencies, "neutral");
            } else {
                incrementSentimentFrequency(sentimentFrequencies, "negative");
            }
        }
        return sentimentFrequencies;
    }

    private static Map<String, Double> getBlankSentimentMap() {
        Map<String, Double> sentimentFrequency = new HashMap<String, Double>();

        sentimentFrequency.put("positive", 0.0);
        sentimentFrequency.put("neutral", 0.0);
        sentimentFrequency.put("negative", 0.0);

        return sentimentFrequency;
    }

    private static void incrementSentimentFrequency(Map<String, Double> map, String sentiment) {
        Double currentSentimentCount = map.get(sentiment);
        Double newSentimentCount = currentSentimentCount + 1;
        map.put(sentiment, newSentimentCount);
    }
}
