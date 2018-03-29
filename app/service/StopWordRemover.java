package service;

import repositories.StopWordRepository;

import java.util.ArrayList;
import java.util.List;

public class StopWordRemover {

    private static List<String> stopWords;
    private static StopWordRepository stopWordRepo;

    public static List<String> removeStopWords(List<String> words) {
        initRepo();
        stopWords = stopWordRepo.getStopWords();

        List<String> swOmitted = new ArrayList<>();

        for (String word : words) {
            word = word.toLowerCase();
            if (!stopWords.contains(word)) {
                swOmitted.add(word);
            }
        }
        return swOmitted;
    }

    private static void initRepo() {
        if (stopWordRepo == null) {
            stopWordRepo = new StopWordRepository("util", "stopwords");
        }
    }
}