package net.sparkzz.servercontrol.util;

import org.bukkit.ChatColor;

/**
 * Created by Brendon on 7/3/2014.
 */
public class Colorizer {

	//  colors
	public ChatColor
		BLACK = ChatColor.BLACK,
		DARK_GRAY = ChatColor.DARK_GRAY,
		GRAY = ChatColor.GRAY,
		WHITE = ChatColor.WHITE,
		DARK_RED = ChatColor.DARK_RED,
		RED = ChatColor.RED,
		BLUE = ChatColor.DARK_BLUE,
		LIGHT_BLUE = ChatColor.BLUE,
		CYAN = ChatColor.AQUA,
		GREEN = ChatColor.DARK_GREEN,
		LIGHT_GREEN = ChatColor.GREEN,
		PURPLE = ChatColor.DARK_PURPLE,
		PINK = ChatColor.LIGHT_PURPLE,
		GOLD = ChatColor.GOLD,
		YELLOW = ChatColor.YELLOW;


	// formats
	public ChatColor
		BOLD = ChatColor.BOLD,
		ITALIC = ChatColor.ITALIC,
		UNDERLINE = ChatColor.UNDERLINE,
		RESET = ChatColor.RESET;

	// custom
	public String
		INFO = GRAY + "" + ITALIC,
		SEVERE = BOLD + "" + RED;

	public String translateColorCodes(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public String deColorize(String message) {
		return ChatColor.stripColor(message);
	}
}