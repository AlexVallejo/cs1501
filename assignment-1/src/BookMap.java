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

            do {
                line = input.readLine();
                line = line.toLowerCase();

                //addWordsToTable(line);
                words = line.split("[\\W_]");

                for (String word : words){
                    if (word.equals(""))
                        continue;

                    else if (map.get(word) == null)
                        map.put(word,1);

                    else
                        map.put(word, 1 + map.get(word));
                }

                lines++;
            }while (line != null);
        }

        catch (Exception ex){
        }

        Collection<Integer> wordCollection = map.values();
        totalWords = 0;
        for (Integer occurrences : wordCollection){
            totalWords += occurrences;
        }
    }

    public void addWordsToTable(String line){
        int prev = 0;

        for (int cur = 0; cur < line.length(); cur++){
            char c = line.charAt(cur);

            if (!Character.isLetterOrDigit(c) || cur == line.length() - 1){
                if (cur == line.length() -1)
                    cur++;

                String sub = line.substring(prev, cur);
                Integer value = map.get(sub);

                if (value == null)
                    map.put(sub, 1);
                else
                    map.put(sub, value + 1);
                prev = cur + 1;
            }
        }
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

    public String printMessages(){
        
    	String message;
    	
    	if (valid)
    		message = "File " + filename  + ": " + lines + " lines, " + totalWords + " words, " + map.size() + " distinct words";
        
    	else
    		message = filename + " not found!";

    	System.out.println(message);
    	return message;
    }

    public String printMap(){
        StringBuffer sb = new StringBuffer();
    	
        for (String word : map.keySet())
        	sb.append(word);
        
        System.out.println(sb);
    	
    	return sb.toString();
    }
}