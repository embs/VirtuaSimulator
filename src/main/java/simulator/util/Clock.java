package simulator.util;

public class Clock {
	private static Clock instance;
	private int time;

	private Clock() {
		time = 0;
	}

	public static Clock instance() {
		if(instance == null) {
			instance = new Clock();
		}

		return instance;
	}

	public int time() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}
}
