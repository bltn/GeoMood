package service;

import controllers.routes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StopWordRemover {

    private static List<String> stopWords;
    private static String stopWordFilePath;

    public static List<String> removeStopWords(String sentence) {
        sentence = sentence.toLowerCase();
        List<String> words = Arrays.asList(sentence.split("\\s+"));

        for (String word : words)
            if (stopWords.contains(word)) words.remove(words);

        return words;
    }

    private static void initStopWordList() {
        if (stopWords == null) {
            stopWordFilePath = controllers.routes.Assets.at("files/stopwords.txt").toString();
            stopWords = new ArrayList<>();

            try {
                Stream<String> stream = Files.lines(Paths.get(stopWordFilePath));
                stream.forEach(stopWords::add);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}