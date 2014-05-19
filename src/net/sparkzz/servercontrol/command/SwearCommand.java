package net.sparkzz.servercontrol.command;

import java.util.List;

import net.sparkzz.servercontrol.Main;
import net.sparkzz.servercontrol.event.Events;
import net.sparkzz.servercontrol.event.SwearListener;
import net.sparkzz.util.Colorizer;
import net.sparkzz.util.FileManager;
import net.sparkzz.util.MsgHandler;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SwearCommand implements CommandExecutor {

	Main main = Main.getInstance();
	FileManager file = Main.getManager();
	MsgHandler msg = MsgHandler.getInstance();
	Colorizer color = Colorizer.getInstance();
	SwearListener swear = SwearListener.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("server.swear.add") || !sender.hasPermission("server.swear.remove")) {
			msg.deny(sender);
			return true;
		}
		
		if (args.length < 1) {
			msg.args(sender, 1);
			return true;
		}
		
		boolean swearProtect = file.getConfig().getBoolean("messaging.clean.enabled");
		
		String key = null, value = null;
		
		if (args[0].equalsIgnoreCase("add")) {
			if (!sender.hasPermission("server.swear.add")) {
				msg.deny(sender);
				return true;
			}
			
			String str = msg.buildString(1, args);
			String[] message = null;
			
			for (int i = 0; i < str.length(); i++) {
				message = str.split(", ");
				
				if (message.length < 2) {
					msg.args(sender, 1);
					return true;
				}
				
				key = message[0].toLowerCase();
				value = message[1];
			}
			
			if (swearProtect) {
				swear.addSwear(key, value);
			}
			
			List<String> swearlist = file.getConfig().getStringList("swears");
			
			swearlist.add(key + ":" + value);
			
			file.getConfig().set("swears", swearlist);
			file.saveConfig();
			
			msg.send(sender, color.GREEN + "New swear: " + color.GOLD + key + color.GREEN + " to be replaced with " + color.GOLD + value);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("remove")) {
			if (!sender.hasPermission("server.swear.remove")) {
				msg.deny(sender);
				return true;
			}
			
			if (args.length > 2) {
				msg.args(sender, 2);
				return true;
			}
			
			if (swearProtect) {
				swear.removeSwear(key);
			}
		}
		
		if (args[0].equalsIgnoreCase("reload")) {
			if (!sender.hasPermission("server.swear.remove")) {
				msg.deny(sender);
				return true;
			}
			
			if (args.length > 1) {
				msg.args(sender, 2);
				return true;
			}
			
			if (swearProtect) {
				swear.reloadSwears();
			} else {
				msg.send(sender, msg.warn("The swear protection is not enabled!"));
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("enable")) {
			if (!sender.hasPermission("server.swear.enable")) {
				msg.deny(sender);
				return true;
			}
			
			if (!swearProtect) {
				Events.registerEvents(new SwearListener(), main);
				swear.createSwearList();
				return true;
			} else {
				msg.send(sender, msg.warn("Swear protection is already enabled!"));
				return true;
			}
		}
		
		if (args[0].equalsIgnoreCase("disable")) {
			if (!sender.hasPermission("server.swear.disable")) {
				msg.deny(sender);
				return true;
			}
			
			if (swearProtect) {
				swear.clearSwears();
			} else {
				msg.send(sender, msg.warn("Swear protection is already disabled!"));
				return true;
			}
		}
		return true;
	}
}