package net.sparkzz.servercontrol.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

/**
 * Created by Brendon on 7/3/2014.
 */
public enum Options {

	ADMINS_ONLY, ADMINS_ONLY_KICK_MSG, JOIN_MESSAGE, LOWERCASE_CHAT,
	MOTD_INGAME, MOTD_SERVER, QUIT_MESSAGE, SERVER_NAME, SWEAR_PROTECT,
	SWEAR_PROTECT_MODE, SWEAR_PROTECT_STRICT, SWEAR_WARN, SWEAR_WARNING;

	private static HashMap<Options, Boolean> options = new HashMap<Options, Boolean>();
	private static HashMap<Options, Integer> values = new HashMap<Options, Integer>();
	private static HashMap<Options, String> messages = new HashMap<Options, String>();

	private static FileManager files = FileManager.getManager();
	private static FileConfiguration config = files.getConfig();
	private static FileConfiguration data = files.getData();

	public static boolean getOption(Options option) {
		return options.get(option);
	}

	public static int getValue(Options option) {
		return values.get(option);
	}

	public static String getMessage(Options option) {
		return messages.get(option);
	}

	public static void setupOptions() {
		options.put(ADMINS_ONLY, config.getBoolean("admins_only.enabled"));
		options.put(LOWERCASE_CHAT, config.getBoolean("chat.lowercase"));
		options.put(SWEAR_PROTECT, config.getBoolean("chat.clean.enabled"));
		options.put(SWEAR_PROTECT_STRICT, config.getBoolean("chat.clean.strict"));
		options.put(SWEAR_WARN, config.getBoolean("chat.clean.warn.enabled"));

		messages.put(ADMINS_ONLY_KICK_MSG, config.getString("admin_only.kick_message"));
		messages.put(JOIN_MESSAGE, config.getString("messages.join"));
		messages.put(MOTD_INGAME, config.getString("motd.ingame"));
		messages.put(MOTD_SERVER, config.getString("motd.server"));
		messages.put(QUIT_MESSAGE, config.getString("messages.quit"));
		messages.put(SERVER_NAME, config.getString("server_name"));
		messages.put(SWEAR_WARNING, config.getString("chat.clean.warn.message"));

		values.put(SWEAR_PROTECT_MODE, config.getInt("chat.clean.mode"));
	}

	public static void setMessage(Options option, String message) {
		messages.put(option, message);
	}

	public static void setOption(Options option, boolean value) {
		options.put(option, value);
	}

	public static void setValue(Options option, int value) {
		values.put(option, value);
	}

	public static void reload() {
		messages.clear();
		options.clear();
		values.clear();

		setupOptions();
	}
}