/*CS3331 - Advanced Object-oriented programming - Dr.Roach - M,W 3:00pm - 4:20pm
 * This program simulates a track where n number of runners can participate. Each runner has a specified
 * top speed and acceleration, and a position (distance) based on arbitrary 'time'. The timer will stop running
 * only after all runners have finished the track.  
 * */

import java.lang.Math;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class runner{
	
	private String name;
	private double topSpeed;
	private double acceleration;
	private boolean slowingDown;
	private boolean goingBack;

	
	public runner(String newName, double maxSpeed, double Acceleration) {
		name = newName;
		topSpeed = maxSpeed;
		acceleration = Acceleration;
		slowingDown =  false;
		goingBack = false;
		
		
	}
	
	private void setAcceleration(double newAcceleration) {
		acceleration = newAcceleration;
	}
	
	private String getName() {
		return name;
	}
		
	private double getTopSpeed() {
		return topSpeed;
	}
	
	private double getAcceleration() {
		return acceleration;
	}
	
	private double getVelocity(double time) {
		return round(Math.min(acceleration * time, topSpeed),2);
	}
	
//	private double getDistance(double time) {
//		return round(time * getVelocity(round(time,2)),2);
//	}
	
	private static void printLine(runner[] Runners, double time) { //UNFINISHED
		int i = 0;
		System.out.println(Runners[i].getDistance(time) + "\t" + Runners[i].getVelocity(time) );
	}
	
	private static double getStoppingDistance(runner R) { //Calculate distance to begin slow down.
		return round(Math.pow(R.topSpeed, 2) / (2 * R.acceleration ), 2);
	}
	
	private double getDistance(double time) {
		return (topSpeed * time ) + (.5 * acceleration * time * time);
	}
	
	private static boolean trackStatus(runner[] Runners, double time, double trackLength) { //Count how many runners have traveled the entire track.
		int doneRunners = 0;
		
		for(int i = 0; i < Runners.length; i++) { 
			if (Runners[i].getDistance(round(time,2)) >= trackLength) { //If a runner traveled the track length, count it as done.
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
		double time = 0;
		double trackLength = 600;
		double stoppingValue;

		
		runner[] Runners = new runner[1];
		
		Runners[0] = new runner("Nelly", 30,  8.0);
//		Runners[1] = new runner("Steve", 8.8, 3.0);
//		Runners[2] = new runner("Usain", 41,  11);
		
		while(trackStatus(Runners, round(time,2), trackLength) != true) {
						
			for(int i = 0; i < Runners.length; i++) {
				
				stoppingValue = (trackLength / 2) - getStoppingDistance(Runners[i]); //Get distance at which to start slowing down
			
				if( Runners[i].getDistance(round(time,2)) >= (round(stoppingValue -.2,2)) && //If runner is within range of slowing down, and has not slowed down, change acceleration to negative.
					Runners[i].getDistance(round(time,2)) <= (round(stoppingValue +.2,2)) && 
					Runners[i].slowingDown == false &&
					Runners[i].getDistance(round(time,2)) < trackLength / 2) {
				
					System.out.println("Stopping.......................................... ");
					
					Runners[i].slowingDown = true; //Will allow the statement to execute once per runner.
					Runners[i].setAcceleration(Runners[i].getAcceleration() * -1); //Make acceleration negative, to slow down.
				}
			
				if( Runners[i].getAcceleration() <= 0  && Runners[i].goingBack == false &&
					Runners[i].getDistance(round(time,2)) >= ((trackLength / 2) -.1) &&
					Runners[i].getDistance(round(time,2)) <= ((trackLength / 2) +.1) ){ //If acceleration is negative and a complete stop has been reached, set acceleration to positive.
					
					System.out.println("Acceleration to positive -----------------------------------");
					
					Runners[i].setAcceleration(Runners[i].getAcceleration() * -1); //Change acceleration back to positive.
					Runners[i].goingBack = true; //Will allow the statement to execute once per runner.
				
				}
				
			}
		
			System.out.println("Time " + round(time,2) + " Acceleration " + Runners[0].getAcceleration());
			System.out.println("Distance and velocity :");
			printLine(Runners, time);
			
			
			time += .01; //Increase time
		} 
		


		
	}//close main.
	
	
		
	
}//Close class.


































