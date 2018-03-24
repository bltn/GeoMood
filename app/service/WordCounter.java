package service;

import models.Tweet;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordCounter {

    public static Map<String, Integer> getWordCounts(List<Tweet> tweets) {
        Map<String, Integer> wordOccurences = new HashMap<>();
        for (Tweet tweet : tweets) {
            String tweetText = tweet.getText().toLowerCase();
            List<String> words = Arrays.asList(tweetText.split("\\s+"));
            //List<String> stopWordsOmiited = StopWordRemover.removeStopWords(words);
            for (String word : words) {
                if (!wordOccurences.containsKey(word)) {
                    wordOccurences.put(word, 1);
                } else {
                    wordOccurences.put(word, wordOccurences.get(word) + 1);
                }
            }
        }
        return wordOccurences;
    }
}
