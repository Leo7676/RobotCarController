package sa.robotcar.motor;

import com.pi4j.io.gpio.GpioPinDigitalOutput;

/**
 *	Simple PWM implementation.   
 *
 */
public class PWMMotorImpl implements PWMMotorIf {

	private final GpioPinDigitalOutput motorForwardPin;
	private final GpioPinDigitalOutput motorReversePin;
	private final SpeedController sc;

	public PWMMotorImpl(GpioPinDigitalOutput forward, GpioPinDigitalOutput reverse)
	{
		motorForwardPin = forward;
		motorReversePin = reverse;

		sc = new SpeedController();
		sc.start();
	}

	@Override
	public void forward(int percent_power) {
		sc.forward(percent_power);
	}

	@Override
	public void stop() {
		sc.stopMotor();
	}

	@Override
	public void reverse(int percent_power) {
		sc.reverse(percent_power);
	}

	enum Direction {FORWARD, REVERSE,STOP};	

	class SpeedController extends Thread
	{
		private boolean running = true;
		private Direction movement = Direction.STOP;
		private long sleepLow = 0;
		private long sleepHigh = 0;

		public void forward(int percentage)
		{
			setSleepTimes(percentage);
			movement = Direction.FORWARD;
		}

		public void reverse(int percentage)
		{
			setSleepTimes(percentage);
			movement = Direction.REVERSE;
		}

		private void setSleepTimes(int percentage)
		{
			sleepHigh = percentage/10;
			sleepLow = 10-sleepHigh;
		}

		public void stopMotor()
		{
			movement = Direction.STOP;
		}

		public void exit()
		{
			stopMotor();
			running = false;
		}

		@Override
		public void run() {
			try{
				while(running)
				{
					switch(movement)
					{
					case FORWARD:
						motorReversePin.low();
						motorForwardPin.high();
						sleep(sleepHigh);
						motorForwardPin.low();
						sleep(sleepLow);
						break;
					case REVERSE:
						motorForwardPin.low();
						motorReversePin.high();
						sleep(sleepHigh);
						motorReversePin.low();
						sleep(sleepLow);
						break;
					case STOP:
						motorForwardPin.low();
						motorReversePin.low();
						sleep(10);
						break;
					default:
						break;
					}
				}
			}
			catch(InterruptedException ie)
			{
				System.out.println("Thread Interrupted");
			}
		}
	}
}
