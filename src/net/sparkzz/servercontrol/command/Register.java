package net.sparkzz.servercontrol.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;

/**
 * Created by Brendon on 7/16/2014.
 */
public class Register {

	private static Register register = new Register();

	public static Register getRegister() {
		return register;
	}

	public void initCommand(String command, CommandExecutor executor) {
		Bukkit.getPluginCommand(command).setExecutor(executor);
	}
}