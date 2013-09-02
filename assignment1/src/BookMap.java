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

    public int lines;
    public TreeMap<String, Integer> map;

    //TODO handle exceptions with helpful error messages
    public BookMap(String filename) throws Exception{
        lines = 0;
        map = new TreeMap<String, Integer>();
        String line;
        String[] words;

        try{
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
                        map.put(word,1);

                    else
                        map.put(word, 1 + map.get(word));

                }

            }
        }

        catch (Exception ex){
            System.out.println(filename + " not found!");
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
        //return NaN as instructed if the book is not filled
        if (otherBook.map.size() < 1 || map.size() < 1)
            return 0.0/0.0;

        Collection<Integer> otherFrequencies = otherBook.map.values();
        Collection<Integer> frequencies = map.values();

        //Compute the normalizations of the two vectors
        double norm = 0;
        double otherNorm = 0;

        for (Integer num : frequencies)
            norm += (num * num);

        for (Integer num : otherFrequencies)
            otherNorm += (num * num);

        norm = Math.sqrt(norm);
        otherNorm = Math.sqrt(otherNorm);

        //Compute the inner product of the numbers
        double innerProd = 0;

        for (String word : map.keySet()){
            Integer value = otherBook.map.get(word);

            if (value != null)
                innerProd += value * map.get(word);
        }

        return Math.acos(innerProd / (norm * otherNorm));
    }
}