package sa.robotcar.distancesensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinState;

/**
 * An implementation of HC-SR04 ultrasonic distance sensor 
 */
public class UltraSonicDistanceSensorImpl implements DistanceSensorIf {

	/** The speed of sound in air, in mm/s*/
	private final static double SOUND_SPEED = 343000;

	/** The pin corresponding to the trigger */
	private GpioPinDigitalOutput triggerPin;

	/** The pin corresponding to the echo pin */
	private GpioPinDigitalInput echoPin;

	public UltraSonicDistanceSensorImpl(Pin trigger, Pin echo)
	{
		// create gpio controller
		final GpioController gpio = GpioFactory.getInstance();

		triggerPin = gpio.provisionDigitalOutputPin(trigger, "Trig", PinState.LOW);
		echoPin = gpio.provisionDigitalInputPin(echo, "Echo");
	}

	@Override
	public double getDistance() 
	{
		double start = 0;
		double end = 0;

		// Activate the device by sending a 10 microsecond output to the trigger pin 
		triggerPin.high();
		try 
		{
			Thread.sleep(0, 10000);
		} 
		catch (InterruptedException e) 
		{
			
		} 
		triggerPin.low();

		// TODO:  Test out the difference in using the GpioDigitalPinListener vs. this loop 
		while (echoPin.isLow())
			start = System.nanoTime();
		while (echoPin.isHigh())
			end = System.nanoTime();

		double pulseDuration = (end - start) / 1000000000d; 
		return pulseDuration * SOUND_SPEED/2;
	}
}
