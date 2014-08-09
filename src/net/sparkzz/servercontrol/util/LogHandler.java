package net.sparkzz.servercontrol.util;

import net.sparkzz.servercontrol.Main;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Brendon on 7/4/2014.
 */
public class LogHandler {
	private Logger logger = Logger.getLogger("Minecraft");

	public void log(Level level, String message) {
		logger.log(level, "[ServerControl] " + message);
	}

	public void info(String message) {
		logger.log(Level.INFO, "[ServerControl] " + message);
	}

	public void severe(String message) {
		logger.log(Level.SEVERE, "[ServerControl] " + message);
	}

	public void warn(String message) {
		logger.log(Level.WARNING, "[ServerControl] " + message);
	}
}