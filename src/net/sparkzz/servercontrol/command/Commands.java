package net.sparkzz.servercontrol.command;

import net.sparkzz.servercontrol.commands.*;

/**
 * Created by Brendon on 7/16/2014.
 */
public class Commands {

	private static Register register = Register.getRegister();

	public static void initCommands() {
		// general commands
		register.initCommand("broadcast", new BroadcastCommand());
		register.initCommand("motd", new MOTDCommand());
		register.initCommand("ops", new OpsCommand());

		// moderation commands
		register.initCommand("swear", new SwearCommand());

		// player manipulation commands
		register.initCommand("gamemode", new GameModeCommand());
		register.initCommand("invsee", new InvseeCommand());

		// communication commands
		register.initCommand("message", new MessageCommand());
		register.initCommand("reply", new ReplyCommand());
		register.initCommand("whisper", new WhisperCommand());

		// server commands
		register.initCommand("adminsonly", new AdminsOnlyCommand());
		register.initCommand("world", new WorldCommand());
	}
}