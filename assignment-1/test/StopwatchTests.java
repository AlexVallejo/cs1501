import static org.junit.Assert.*;
import org.junit.Test;

public class StopwatchTests {

	@Test
	public void testStopwatchMillis(){
    	try {
    		long startTime = System.currentTimeMillis();
    		Stopwatch watch = new Stopwatch();
    		Thread.sleep(100);
    		long endFromSystemTime = System.currentTimeMillis();
    		double endtime = (startTime - endFromSystemTime) / 100.0;
    		assertEquals(endtime, watch.elapsedTimeMillis(), 3);
    	}
    	catch (Exception ex){ }
    }
	
	@Test
	public void testStopwatchSeconds(){
		try {
			long startTime = System.currentTimeMillis();
    		Stopwatch watch = new Stopwatch();
    		Thread.sleep(100);
    		long endFromSystemTime = System.currentTimeMillis();
    		double endtime = (startTime - endFromSystemTime) / 1000.0;
    		assertEquals(endtime, watch.elapsedTimeMillis(), 3);
		}
		catch (Exception ex) { }
	}
}
