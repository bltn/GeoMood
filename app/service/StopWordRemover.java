package service;

import controllers.routes;
import scala.xml.Source;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class StopWordRemover {

    private static List<String> stopWords;
    private static String stopWordFilePath;

    public static List<String> removeStopWords(List<String> words) {
        initStopWordList();

        for (String word : words) {
            word = word.toLowerCase();
            if (stopWords.contains(word)) {
                words.remove(word);
            }
        }

        return words;
    }

    private static void initStopWordList() {
        if (stopWords == null) {
            stopWordFilePath = "/stopwords.txt";
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