// CS 401 Fall 2019
// Assignment 2 MyTimer class
// Use this class to add a timer to your word conversion program.  For some help 
// on using this class, see program TimerTest.java

// Note: There are some things (such as Threads and synchronized methods) in this
// implementation with which you may not be familiar.  However, as we discussed
// in lecture, due to data abstraction, you do not need to know these implementation
// details in order to utilize this class.  You simply need to understand what this
// class does in general, what the methods do and their headers (specifications).
// Therefore, for Assignment 2 focus on the descriptions of the methods rather than
// on the actual code within them.

public class MyTimer implements Runnable
{
	private boolean running;
	private long duration;
	private long curr, end;
	
	// Initialize a new MyTimer object.  This will create the object and
	// initialize the duration of the timer to millis milliseconds
	public MyTimer(long millis)
	{
		running = false;
		duration = millis;
	}
	
	// Start the timer running.  It will continue to run until the set time
	// expires.
	public synchronized void start()
	{
		if (!running)
		{
			curr = System.nanoTime();
			end = curr + duration*1000000;  // convert millis to nanoseconds
			running = true;
			new Thread(this).start();
		}
		else
			System.out.println("Timer already running...not started");
	}
	
	// This method will return true as long as the timer is running.  It will
	// return false when the timer is not running.
	public synchronized boolean check()
	{
		return running;
	}
	
	// This method is necessary for the timer but the user of the timer does
	// not need to know it -- it is abstracted out.
	public void run()
	{
		while (running)
		{
			try 
			{
				Thread.sleep(20);
			}
			catch (InterruptedException e)
			{
				System.out.println("Thread Error");
			}
			curr = System.nanoTime();
			if (curr >= end)
				running = false;
		}
	}
	
	public synchronized void stop()
	{
		running = false;
		// Stall to let the run method iterate
		try 
		{
			Thread.sleep(20);
		}
		catch (InterruptedException e)
		{
			System.out.println("Thread Error");
		}
	}
	
	
}