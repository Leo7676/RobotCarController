package sa.robotcar.distancesensor;

import sa.robotcar.server.ServerCommandIf;
import sa.robotcar.server.SimpleSocketServer;

import com.pi4j.io.gpio.RaspiPin;

public class DistanceController implements ServerCommandIf{

	DistanceSensorIf distanceSensor;
	SimpleSocketServer server;
	
	public DistanceController(){
		System.out.println("DistanceController Started");
		distanceSensor = new UltraSonicDistanceSensorImpl(RaspiPin.GPIO_04, RaspiPin.GPIO_05);
		server = new SimpleSocketServer("DistanceServer", 4444, this);
		Thread sss = new Thread(server);
		sss.start();
	}

	@Override
	public String onCommand(String command) {
		String response = null;
		switch(Command.valueOf(command))
		{
		case GET_DISTANCE:
			response = String.valueOf(distanceSensor.getDistance());
			break;
		default:
			break;
		}
		return response;
	}
	
	enum Command
	{
		GET_DISTANCE;
	}

}
