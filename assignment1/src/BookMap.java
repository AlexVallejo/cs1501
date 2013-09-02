/**
 * Created By: Alex Vallejo
 * Date: 9/1/13
 * Project: assignment1
 * Notes:
 */
import java.io.File;
import java.util.Collection;
import java.util.Scanner;
import java.util.TreeMap;

public class BookMap {

    int lines;
    TreeMap<String, Integer> map;

    //TODO handle exceptions with helpful error messages
    public BookMap(String filename) throws Exception{
        lines = 0;
        map = new TreeMap<String, Integer>();
        String line;
        String[] words;
        Scanner input = new Scanner(new File(filename));

        while (input.hasNext()){
            line = input.nextLine();
            lines++;
            line = line.replaceAll("[^a-zA-Z0-9\\s]", " ");
            line = line.toLowerCase();
            words = line.split(" ");

            for (String word : words){
                if (word.equals(""))
                    continue;

                if (map.get(word) == null)
                    map.put(word,0);

                else
                    map.put(word, 1 + map.get(word));

            }

        }

        Collection<Integer> wordCollection = map.values();
        int totalWords = 0;
        for (Integer occurrences : wordCollection){
            totalWords += occurrences;
        }

        System.out.println("File " + filename  + ": " + lines + " lines, "
                + totalWords + " words, " + map.size() + " distinct words");

    }

    public double distanceBetween(BookMap otherBook){
        return 0.0;
    }
}