package sa.robotcar.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import sa.robotcar.server.ServerCommandIf;
import sa.robotcar.server.SimpleSocketServer;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class MotorController implements ServerCommandIf{

	private final GpioController gpio = GpioFactory.getInstance();
	private Map<Integer, PWMMotorIf> motorMap;
	SimpleSocketServer server;
	
	public MotorController()
	{
		System.out.println("Motor controller started.");
		initMotors();
		server = new SimpleSocketServer("MotorServer", 1234, this);
		Thread sss = new Thread(server);
		sss.start();
	}
	
	private void initMotors()
	{
		motorMap = new HashMap<Integer, PWMMotorIf>();

		GpioPinDigitalOutput leftMotorForwardPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "leftForward", PinState.LOW);
		GpioPinDigitalOutput leftMotorReversePin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "leftReverse", PinState.LOW);
		GpioPinDigitalOutput rightMotorForwardPin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "rightForward", PinState.LOW);
		GpioPinDigitalOutput rightMotorReversePin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "rightReverse", PinState.LOW);
		
		PWMMotorIf leftMotor = new PWMMotorImpl(leftMotorForwardPin,leftMotorReversePin);
		PWMMotorIf rightMotor = new PWMMotorImpl(rightMotorForwardPin,rightMotorReversePin);
		
		motorMap.put(0, leftMotor);
		motorMap.put(1, rightMotor);		
	}
	
	public void setMotorState(int motorid, int speed)
	{
		PWMMotorIf motor = motorMap.get(motorid);
		if(motor == null) 
		{
			System.out.println("Motor ID not found");
			return;
		}

		if(speed == 0)
			motor.stop();
		else if (speed < 0)
		{
			System.out.println("Reverse at speed "+speed);
			motor.reverse(Math.abs(speed));
		}
		else
		{
			System.out.println("forward at speed "+speed);
			motor.forward(speed);
		}
		System.out.println("Return");
	}
	
	/**
	 *  stop all GPIO activity/threads by shutting down the GPIO controller
	 * (this method will forcefully shutdown all GPIO monitoring threads and scheduled tasks)
	 */
	public void shutdown()
	{
		gpio.shutdown();
	}
	
	@Override
	public String onCommand(String command) {
		StringTokenizer st = new StringTokenizer(command);
		int motorId = Integer.parseInt(st.nextToken());
		int speed  = Integer.parseInt(st.nextToken());
		System.out.println("Parsed "+motorId+","+speed);
		setMotorState(motorId, speed);
		return null;

	}

}
