package sa.robotcar.motor;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 * A simple implementation of a motor controller
 */
public class SimpleMotorImpl implements MotorIf
{
	private final GpioPinDigitalOutput motorForwardPin;
	private final GpioPinDigitalOutput motorReversePin;

	public SimpleMotorImpl(GpioPinDigitalOutput forward, GpioPinDigitalOutput reverse)
	{
		motorForwardPin = forward;
		motorReversePin = reverse;
	}

	@Override
	public void forward() {
		motorForwardPin.high();
		motorReversePin.low();
	}

	@Override
	public void reverse() {
		motorForwardPin.low();
		motorReversePin.high();
	}

	@Override
	public void stop() {
		motorForwardPin.low();
		motorReversePin.low();
	}
}
