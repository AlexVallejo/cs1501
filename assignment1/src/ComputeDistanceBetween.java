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

        BookMap book1 = new BookMap(args[0]);
        BookMap book2 = new BookMap(args[1]);

        System.out.println("The distance between the documents is " +
                book1.distanceBetween(book2));
        System.out.println("Elapsed time in seconds: " + watch.elapsedTime());

    }//end main
}//end ComputeDistanceBetween