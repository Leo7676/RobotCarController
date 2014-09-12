package sa.robotcar.server;

public interface ServerCommandIf {
	
	/**
	 * Callback for commands
	 * 
	 * @param command The string command
	 * @return Returns a result.  May be null if not response required.
	 */
	public String onCommand(String command);
}
