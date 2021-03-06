import lejos.nxt.*;
/**
 * RoboRace car - PID controlled line follower 
 * and state machine to control turns and stop at end of game
 * 
 * @author  Kim Bjerge
 * @version 01.10.2010
 */
public class RoboRace implements ButtonListener 
{
  private static int dT = 1000;   // seconds
  private boolean keepItRunning = true;
  private boolean stopped = false;
  private static LineFollowerPID lineFollower;
  private static StateController stateController;

  public RoboRace() {
  }

  public void buttonPressed(Button b){
	  
	  if (b == Button.ESCAPE)
		  keepItRunning = false;
	  
	  if (b == Button.ENTER)
	  {
		  stopped = !stopped;
	      lineFollower.stop(stopped);
	  }
	  if (b == Button.LEFT)
	  {
		  lineFollower.decSensitiv();
	  }
	  if (b == Button.RIGHT)
	  {
	     lineFollower.incSensitiv(); 
	  }
  }

  public void buttonReleased(Button b){}
  
  public void run() throws Exception
  {
 	 int time = 0; 
 	 Button.ESCAPE.addButtonListener(this);
 	 Button.ENTER.addButtonListener(this);
 	 Button.LEFT.addButtonListener(this);
 	 Button.RIGHT.addButtonListener(this);
     
 	 while (keepItRunning)
     {    	 
	     LCD.drawInt(lineFollower.getLight(),4,10,2);
	     LCD.drawInt(time++,4,10,3);
	     LCD.refresh();     	     	     
	     Thread.sleep(dT);
     }
     
     Car.stop();
     LCD.clear();
     LCD.drawString("Program stopped", 0, 0);
     LCD.refresh();    
  }
  
  public static void main (String[] aArg)
  throws Exception
  {	 
	 lineFollower = new LineFollowerPID(); 
	 lineFollower.calibrate();
	 stateController = new StateController(lineFollower);	 
	 
	 while (Button.ENTER.isPressed());
	 LCD.drawString("Press ENTER     ", 0, 0);
	 LCD.drawString("to start        ", 0, 1);
	 LCD.drawString("Robot Race      ", 0, 2);
	 while (!Button.ENTER.isPressed());
	 while (Button.ENTER.isPressed());
	 
     LCD.clear();
	 LCD.drawString("JoseMariaKim    ", 0, 0);
	 LCD.drawString("Robot Race      ", 0, 1);
     LCD.drawString("Light: ", 0, 2); 
     LCD.drawString("Time [sec]: ", 0, 3); 
  
     lineFollower.setDaemon(true);
     lineFollower.start();
     stateController.setDaemon(true);
     stateController.start();
   	 
     RoboRace roboRace = new RoboRace();
     roboRace.run();
     
   }
}
