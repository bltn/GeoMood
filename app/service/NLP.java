package service;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import twitter4j.Status;

import java.util.*;


public class NLP {
    static StanfordCoreNLP pipeline;

    public static Map<String, Integer> getSentimentMapping(List<Status> tweets) {
        init();

        Map<String, Integer> sentimentMapping = new HashMap<String, Integer>();

        for (Status tweet : tweets) {
            sentimentMapping.put(tweet.getText(), getDominantSentiment(tweet.getText()));
        }
        return sentimentMapping;
    }

    public static int getSentiment(String tweet) {
        init();
        return getDominantSentiment(tweet);
    }

    private static int getDominantSentiment(String tweet) {
        int dominantSentiment = 0;
        if (tweet != null && tweet.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(tweet);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    dominantSentiment = sentiment;
                    longest = partText.length();
                }

            }
        }
        return dominantSentiment;
    }

    private static void init() {
        pipeline = new StanfordCoreNLP("StanfordCoreNLP.properties");
    }
}