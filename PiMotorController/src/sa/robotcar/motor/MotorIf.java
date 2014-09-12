package sa.robotcar.motor;

/**
 * A simple motor interface.  
 */
public interface MotorIf {
	
	/**
	 * Moves the motor forward
	 */
	public void forward();
	
	/**
	 * Moves the motor in reverse
	 */
	public void reverse();
	
	/**
	 * Stops the motor
	 */
	public void stop();
}
