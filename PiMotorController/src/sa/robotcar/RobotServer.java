package sa.robotcar;

import java.io.IOException;

import sa.robotcar.distancesensor.DistanceController;
import sa.robotcar.motor.MotorController;

/**
 * How to run:
 * 1. Export jar file
 * 2. sudo java -cp .:classes:/opt/pi4j/lib/'*':./RobotServer.jar sa.robotcar.RobotServer
 */
public class RobotServer {
	public static void main(String[] args) throws IOException,  java.lang.InterruptedException
	{
		// Start the controllers.  They run in seperate threads
		MotorController mc = new MotorController();
		DistanceController dc = new DistanceController();
	}
}
