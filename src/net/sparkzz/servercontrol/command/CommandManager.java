package net.sparkzz.servercontrol.command;

import net.sparkzz.servercontrol.util.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Brendon on 7/16/2014.
 */
public abstract class CommandManager implements CommandExecutor {

	public Colorizer color = Colorizer.getColor();
	public FileManager files = FileManager.getManager();
	public LogHandler logger = LogHandler.getLogger();
	public MsgHandler msg = MsgHandler.getHandler();
	public Options options;

	private String permission, usage;

	public CommandManager(String permission, String usage) {
		this.permission = permission;
		this.usage = usage;
	}

	public abstract boolean command(CommandSender sender, Command command, String[] args);

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if (!(sender.hasPermission(permission))) {
			msg.deny(sender);
			return true;
		}

		if (!(command(sender, command, args))) {
			sender.sendMessage(msg.info("Usage: " + usage));
			return true;
		}
		return false;
	}
}