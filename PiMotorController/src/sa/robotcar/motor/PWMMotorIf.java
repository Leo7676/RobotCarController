package sa.robotcar.motor;

/**
 * An interface to control a motor using pulse width modulation
 */
public interface PWMMotorIf {

	/**
	 * Moves the motor forward 
	 * 
	 * @param percent_power Power to move the motor forward.  From 0 to 100%
	 */
	public void forward(int percent_power);
	
	/**
	 * Moves the motor forward 
	 * 
	 * @param percent_power Power to move the motor forward.  From 0 to 100%
	 */
	public void reverse(int percent_power);
	
	/**
	 * Stops the motor 
	 */
	public void stop();
}
