package entity;

public enum Command {

	STATUS("/status"), UPTIME("/uptime");
	String command;

	Command(String command) {
		this.command = command;
	}

	public String getCommand() {
		return command;
	}

}
