package net.sparkzz.servercontrol.command;

import net.sparkzz.command.Register;

public class Commands {
	
	Register register = Register.getRegister();
	
	public void initCommands() {
		// general commands
		register.initCommand("motd", new MOTDCommand());
		register.initCommand("broadcast", new BroadcastCommand());
		
		// moderation commands
		register.initCommand("swear", new SwearCommand());
		
		// player manipulation commands
		register.initCommand("gamemode", new GameModeCommand());
		
		// fun commands
		register.initCommand("invsee", new InvseeCommand());
		register.initCommand("message", new MessageCommand());
		register.initCommand("whisper", new WhisperCommand());
		
		// server commands
		register.initCommand("adminsonly", new AdminsOnlyCommand());
		// wip: register.initCommand("world", new WorldCommand());
	}
}