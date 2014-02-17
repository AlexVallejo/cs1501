import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookMapTests {

    private static ArrayList<BookMap> bookList;
    private static String[] fileNames;

    @BeforeClass
    public static void testSetup() {

        bookList = new ArrayList<BookMap>();
        fileNames = new String[]{"mobydick.txt","dickens.txt",
                "merchant.txt", "theprince.txt", "warandpeace.txt"};

        for (int i = 0; i < fileNames.length; i++){
            bookList.add(new BookMap(fileNames[i]));
        }
    }

    @AfterClass
    public static void testCleanup() {
    }
    
    @Test
    public void testFilename() {

        for (int i = 0; i < bookList.size(); i++){
            assertEquals(fileNames[i], bookList.get(i).filename);
        }
    }

    @Test
    public void testDistanceZero(){

        for (BookMap book : bookList)
            assertEquals(0.0, book.distanceBetween(book), 0.000001);
    }

    @Test
    public void testInvalidFileMessage(){
    	BookMap mock = new BookMap("bad_file.txt");
    	String expectedMessage = "bad_file.txt not found!";
    	
    	assertEquals(expectedMessage, mock.printMessages());
    }
    
    @Test
    public void testValidFileMessage(){
    	BookMap mock = new BookMap("brown_fox.txt");
    	String message = "filename brown_fox.txt: 1 lines 9 words 8 distinct words";
    	
    	assertEquals(message, mock.printMessages());
    }
    
    @Test
    public void testEqualityWithExamples(){
        BookMap mobydick = new BookMap("balls.txt");
        BookMap theprince = new BookMap("bats.txt");
        BookMap merchant = new BookMap("gloves.txt");
        BookMap dickens = new BookMap("bases.txt");

        for (BookMap book : bookList){
            if (book.filename.equals("mobydick.txt"))
                mobydick = book;
            else if (book.filename.equals("theprince.txt"))
                theprince = book;
            else if (book.filename.equals("merchant.txt"))
                merchant = book;
            else if (book.filename.equals("dickens.txt"))
                dickens = book;
        }

        assertEquals(0.350215, mobydick.distanceBetween(theprince), 0.000001);
        assertEquals(0.350215, theprince.distanceBetween(mobydick), 0.000001);

        assertEquals(1.141068, theprince.distanceBetween(merchant), 0.000001);
        assertEquals(1.141068, merchant.distanceBetween(theprince), 0.000001);

        assertEquals(0.324192, dickens.distanceBetween(theprince), 0.000001);
        assertEquals(0.324192, theprince.distanceBetween(dickens), 0.000001);

        assertEquals(9, merchant.lines, 0);
        assertEquals(54, merchant.totalWords, 0);
        assertEquals(38, merchant.map.size(), 0);

        assertEquals(45913, dickens.lines, 0);
        assertEquals(413896, dickens.totalWords, 0);
        assertEquals(17704, dickens.map.size(), 0);

        assertEquals(17503, mobydick.lines, 0);
        assertEquals(210028, mobydick.totalWords, 0);
        assertEquals(16834, mobydick.map.size());

        assertEquals(4666, theprince.lines, 0);
        assertEquals(49714, theprince.totalWords, 0);
        assertEquals(5216, theprince.map.size());
    }

    @Test
    public void testInvalidity(){
        BookMap badFileName = new BookMap("bad.txt");

        assertFalse(badFileName.valid);
    }
    
    @Test
    public void testInvalidFilenameDistance(){
    	BookMap badFileName = new BookMap("bad.txt");
    	
    	assertEquals(Double.NaN,bookList.get(0).distanceBetween(badFileName),0);
    }

    @Test
    public void testToString(){
    	BookMap mock = new BookMap("brown_fox.txt");
    	String text = "The quick brown fox jumped over the lazy dog";
    	
    	assertEquals(mock.printMap(), text);    	
    }
    
    @Test
    public void testFileCombinations(){

        ArrayList<BookMap> duplicateList = new ArrayList<BookMap>();

        for (int i = 0; i < fileNames.length; i++){
            duplicateList.add(new BookMap(fileNames[i]));
        }

        for (BookMap book : bookList){
            for (BookMap otherbook : duplicateList){
                if (book.filename.equals(otherbook.filename))
                    continue;
                else{
                    System.out.printf("Distance between %s & %s is: %.6f\n",
                            book.filename, otherbook.filename,
                            book.distanceBetween(otherbook));
                }
            }
            System.out.print("\n");
        }

    }

    @Test
    public void testSpeed(){
        double runTime;

        Stopwatch watch = new Stopwatch();
        new BookMap(fileNames[fileNames.length - 1]);
        runTime = watch.elapsedTimeSeconds();

        watch = new Stopwatch();

        BookMap book = new BookMap(fileNames[fileNames.length - 1]);
        book.distanceBetween(book);
        
        assertTrue(runTime < 0.5);

        System.out.println("Run time: " + runTime);
    }
}