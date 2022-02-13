package helpers;

import org.lwjgl.Sys;

public class Clock {
	
	private static boolean paused = false;
	public static long lastFrame, totalTime;
	public static float d = 0, multiplier = 1;
	/**
	 * local time
	 * @return local time
	 */
	public static long getTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	/**
	 * getDelta is very useful in helping me determine real life seconds for example tower A shoots once every 2.1 seconds
	 * hence that's a float value.
	 * @return
	 */
	public static float getDelta() {
		long currentTime = getTime();
		int delta = (int) (currentTime - lastFrame);
		lastFrame = getTime();
		if (delta * 0.001f > 0.05f)
			return 0.05f;
		return delta * 0.001f;
	}
	
	public static float Delta() {
		if (paused) 
			return 0;
		else 
			return d * multiplier;
	}
	
	public static float TotalTime() {
		return totalTime;
	}
	
	public static float getMultiplier() {
		return multiplier;
	}
	
	public static void Update() {
		d = getDelta();
		totalTime += d;
	}
	/**
	 * for the game to run faster
	 * @param change
	 */
	public static void ChangeMultiplier(float change) {
		if (multiplier + change < -10 && multiplier + change > 10) {
			
		}
		else {
			multiplier += change;
		}
	}
	
	public static void Pause() {
		if (paused)
			paused = false;
		else
			paused = true;
	}
}
