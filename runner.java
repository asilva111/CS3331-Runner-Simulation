/*CS3331 - Advanced Object-oriented programming - Dr.Roach - M,W 3:00pm - 4:20pm
 * This program simulates a track where n number of runners can participate. Each runner has a specified
 * top speed and acceleration, and a position (distance) based on arbitrary 'time'. The timer will stop running
 * only after all runners have finished the track.  
 * 
 * Last updated: Sep 4th, 2019 by Andres Silva - 80588336
 **************************************************************************************************************/

import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class runner{
	
	private String name;
	
	private double topSpeed;
	private double acceleration;
	private double velocity;
	private double distance;
	
	private boolean slowingDown;
	private boolean goingBack;
	private boolean finished;

	
	private static double deltaTime = .01; //Specified change in time.

	public runner(String newName, double maxSpeed, double Acceleration) {
		name = newName;
		topSpeed = maxSpeed;
		acceleration = Acceleration;

		velocity = 0;
		distance = 0;
		
		slowingDown = false;
		goingBack = false;
		finished = false;
			
	}
	
	private void setAcceleration(double newAcceleration) {
		acceleration = newAcceleration;
	}
	
	private double getAcceleration() {
		return acceleration;
	}
	
	private void setVelocity() { 
		if((velocity + (acceleration * deltaTime) <= topSpeed) && velocity + (acceleration * deltaTime) >= 0) {
			velocity += acceleration * deltaTime; //Update velocity only when it has not reached the top speed and is greater than 0.
		}
		else { //Else cap speed at indicated value.
			velocity = topSpeed;
		}
	}
	
	private static void printLine(runner[] Runners, double time) { //Print distance of all runners at specified time.
		System.out.println("\n");
		System.out.print(time);
		
		for(int i = 0; i < Runners.length; i++) { //For all runners print position.
			System.out.print("\t" + round(Runners[i].distance,2));
		}
		
	}
	
	private static void printData(runner[] Runners) { //Print Name/MaxSpeed/Acceleration and names with respective data in a table.
		System.out.println("\nRunner\tMaxSpeed (ft/s) \tAcceleration(f/s/s)");
		for(int i = 0; i < Runners.length; i++) {
			System.out.println(Runners[i].name + "\t" + Runners[i].topSpeed + "\t\t\t" + Runners[i].acceleration);
		}
	}
	
	private static double getStoppingDistance(runner R) { //Calculate distance to begin slow down.
		return round(Math.pow(R.topSpeed, 2) / (2 * R.acceleration ), 2); //Calculate distance needed to stop
		//Formula: FinalVel^2 / 2 * acceleration
	}
	
	private void setDistance() { 
		distance += velocity * deltaTime; 
		//Formula: distance = velocity * deltaTime

	}
	
	private static boolean trackStatus(runner[] Runners, double time, double trackLength) { //Count how many runners have traveled the entire track.
		int doneRunners = 0;
		
		for(int i = 0; i < Runners.length; i++) { 
			if (Runners[i].distance >= trackLength) { //If a runner traveled the track length, count it as done.
				doneRunners++;
			}
		}
		
		if(doneRunners < Runners.length){ //return false if not all runners are done.
			return false;
		}
		
		else { //Else, return true
			return true;
		}
		
	}
	
	public static double round(double value, int places) { // Rounding method taken from: https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	
	public static void main(String args[]) {
		double time = 0; //Clock.
		double trackLength = 600; //Total track length.
		double stoppingValue; //Distance needed to reach 0 velocity.
		int printInterval = 5; //How often to print data.
		
		/**Runner Declaration***********************************************************/
		
		runner[] Runners = new runner[3]; //Number of runners
		
		Runners[0] = new runner("Nelly", 30,  8.0); //Declare each runner
		Runners[1] = new runner("Steve", 8.8, 3.0);
		Runners[2] = new runner("Usain", 41,  11);
		
		/*******************************************************************************/
		
		printData(Runners); //Print name/speed/acceleration table.
		
		System.out.print("\nTime");	 //Print Header of distance table.	
		for(int i = 0; i < Runners.length; i++) {
			System.out.print("\t" + Runners[i].name);
		}
		
		while(trackStatus(Runners, round(time,2), trackLength) != true) { //Stop when all runners are done.
						
			for(int i = 0; i < Runners.length; i++) { //For every runner,
				
				stoppingValue = (trackLength / 2) - getStoppingDistance(Runners[i]); //Get distance at which to start slowing down
			
				/** Slow down */
				
				if(Runners[i].distance >= trackLength) {
					Runners[i].finished = true;
				}
				
				
				if( Runners[i].distance >= (round(stoppingValue -.5,2)) && //If runner is within range of slowing down, and has not slowed down, change acceleration to negative.
					Runners[i].distance <= (round(stoppingValue +.5,2)) && 
					Runners[i].slowingDown == false &&
					Runners[i].distance < trackLength / 2) {
					
					Runners[i].slowingDown = true; //Will allow the statement to execute once per runner.
					Runners[i].setAcceleration(Runners[i].getAcceleration() * -1); //Make acceleration negative, to slow down.
				}
				/** Accelerate back up*/
				
				if( Runners[i].getAcceleration() <= 0  && Runners[i].goingBack == false &&
					Runners[i].distance >= ((trackLength / 2) -.5) &&
					Runners[i].distance <= ((trackLength / 2) +.5) ){ //If acceleration is negative and a complete stop has been reached, set acceleration to positive.
					
					Runners[i].setAcceleration(Runners[i].getAcceleration() * -1); //Change acceleration back to positive.
					Runners[i].goingBack = true; //Will allow the statement to execute once per runner.
				
				}
				
				if(Runners[i].finished == false) {
					Runners[i].setVelocity(); //Update velocity
					Runners[i].setDistance(); //Update distance
				}
				
				
			} //Close for loop.
		
			if(round(time,2) % printInterval == 0) { //Print runner data every 'printInterval' seconds.
				printLine(Runners,round(time,2));
			}	
		
			time += deltaTime; //Increase time
		}//Close while loop. 
		
	}//close main.
	
}//Close class.


































