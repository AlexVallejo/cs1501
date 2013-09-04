/**
 * Created By: Alex Vallejo
 * Date: 9/1/13
 * Project: assignment1
 * Notes:
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Hashtable;

public class BookMap {

    public int lines;
    public Hashtable<String, Integer> map;
    public String filename;
    public int totalWords;
    public boolean valid;

    //TODO handle exceptions with helpful error messages
    public BookMap(String filename){
        lines = 0;
        map = new Hashtable<String, Integer>(50000);
        String line;
        String[] words;
        valid = false;
        this.filename = filename;

        try{
            BufferedReader input = new BufferedReader(new FileReader
                    (filename));

            valid = true;
            line = input.readLine();

            while (line != null){
                line = line.replaceAll("[\\W\\s]", " ");
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

        //todo ensure it returns zero not a small negative exponential
        return Math.acos(innerProd / (norm * otherNorm));
    }
}