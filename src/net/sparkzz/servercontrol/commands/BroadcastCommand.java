package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Created by Brendon on 7/16/2014.
 */
public class BroadcastCommand extends CommandManager {


	public BroadcastCommand() {
		super("server.broadcast", "/broadcast [MESSAGE..]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length == 0) {
			msg.args(sender, -1);
			return false;
		}

		String message = msg.buildString(0, args);

		msg.broadcast(message);
		return true;
	}
}