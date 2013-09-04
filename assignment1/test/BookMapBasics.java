import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class BookMapBasics {

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
    public void testEqualityWithExamples(){
        BookMap mobydick = new BookMap("balls.txt");
        BookMap theprince = new BookMap("bats.txt");

        for (BookMap book : bookList){
            if (book.filename.equals("mobydick.txt"))
                mobydick = book;
            else if (book.filename.equals("theprince.txt"))
                theprince = book;
        }

        assertEquals(0.350215, mobydick.distanceBetween(theprince), 0.000001);
        assertEquals(0.350215, theprince.distanceBetween(mobydick), 0.000001);
        assertEquals(17503, mobydick.lines, 0);
        assertEquals(210028, mobydick.totalWords, 0);
        assertEquals(16834, mobydick.map.size());
        assertEquals(4666, theprince.lines, 0);
        assertEquals(49714, theprince.totalWords, 0);
        assertEquals(5216, theprince.map.size());
    }

    @Test
    public void testErrorOutput(){
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        BookMap badFileName = new BookMap("bad.txt");

        assertFalse(badFileName.valid);
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
}