package net.sparkzz.servercontrol.command;

import net.sparkzz.servercontrol.Main;
import net.sparkzz.util.Colorizer;
import net.sparkzz.util.FileManager;
import net.sparkzz.util.MsgHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MOTDCommand implements CommandExecutor {
	
	FileManager file = Main.getManager();
	MsgHandler msg = MsgHandler.getInstance();
	Colorizer color = Colorizer.getInstance();
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("broadcast")) {
				if (!(sender.hasPermission("server.motd.broadcast"))) {
					msg.deny(sender);
					return true;
				}
				
				msg.broadcast(file.getConfig().getString("motd"));
				return true;
			}
			
			if (args[0].equalsIgnoreCase("view")) {
				if (!(sender.hasPermission("server.motd.view"))) {
					msg.deny(sender);
					return true;
				}
				
				msg.send(sender, file.getConfig().getString("motd"));
				return true;
			}

			msg.args(sender, 3);
			return true;
		}
		
		if (args.length > 1) {
			if (args[0].equalsIgnoreCase("set")) {
				if (!(sender.hasPermission("server.motd.set"))) {
					msg.deny(sender);
					return true;
				}
				
				String message = msg.buildString(1, args);
				
				if (!message.equalsIgnoreCase("")) {
					file.getConfig().set("motd", message);
					msg.send(sender, color.GOLD + "New Message: " + color.RESET + message);
					return true;
				}
				
				msg.send(sender, color.RED + "You must enter a new message!");
				return true;
			}
			
			msg.args(sender, 3);
			return true;
		}
		
		if (args.length == 0) {
			if (!(sender.hasPermission("server.motd.view"))) {
				msg.deny(sender);
				return true;
			}
			
			msg.send(sender, file.getConfig().getString("motd"));
			return true;
		}
		return false;
	}
}
