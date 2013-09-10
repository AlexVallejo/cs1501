/**
 * Created By: Alex Vallejo
 * Date: 8/30/13
 * Project: assignment1
 * Notes:CS 1501
 */
public class ComputeDistanceBetween {

    public static void main(String[] args) throws Exception{
        if (args.length != 2){
            System.out.println("Usage: java ComputeDistanceBetween filename_1" +
                    " filename_2");
            System.exit(1);
        }

        Stopwatch watch = new Stopwatch();

        //If the filenames are equal, the files are equal. Unless the file
        // changes before the initialization of the second book which is
        // highly unlikely. No changes are made to the original file during
        // execution of this program.
        if (args[0].equals(args[1])){
            BookMap book1 = new BookMap(args[0]);
            book1.printMessages();
            book1.printMessages();
            System.out.println("The distance between the documents is: 0" +
                    ".000000 radians");
            System.out.println("Elapsed time in seconds " +
                    watch.elapsedTimeSeconds());
            System.exit(0);
        }

        BookMap book1 = new BookMap(args[0]);
        book1.printMessages();
        //book1.printMap();
        BookMap book2 = new BookMap(args[1]);
        book2.printMessages();

        System.out.printf("The distance between the documents is: %.6f " +
                "radians\n", book1.distanceBetween(book2));
        System.out.println("Elapsed time: " + watch.elapsedTimeSeconds()+" s");

    }//end main
}//end ComputeDistanceBetween