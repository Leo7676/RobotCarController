package sa.robotcar.distancesensor;

/** 
 * A simple interface to define a distance sensor
 **/
public interface DistanceSensorIf {
	
	/**
	 * Gets the distance in millimeters from the nearest object
	 * 
	 * @return distance in millimeters
	 */
	public double getDistance();
}
