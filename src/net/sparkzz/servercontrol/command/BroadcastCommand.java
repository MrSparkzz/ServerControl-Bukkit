package net.sparkzz.servercontrol.command;

import net.sparkzz.util.MsgHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {

	static MsgHandler msg = MsgHandler.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender.hasPermission("server.broadcast"))) {
			msg.deny(sender);
			return true;
		}
		
		if (args.length == 0) {
			msg.args(sender, 1);
			return true;
		}
			
		String message = msg.buildString(0, args);
		
		if (!message.equalsIgnoreCase(""))
			msg.broadcast(message);
		return true;
	}
}