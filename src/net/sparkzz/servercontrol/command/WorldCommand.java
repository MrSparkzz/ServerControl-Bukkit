package net.sparkzz.servercontrol.command;

import net.sparkzz.util.Colorizer;
import net.sparkzz.util.MsgHandler;
import net.sparkzz.util.WorldManager;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WorldCommand implements CommandExecutor {

	WorldManager worlds = WorldManager.getWorlds();
	MsgHandler msg = MsgHandler.getInstance();
	Colorizer color = Colorizer.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		/*
		 * args needed: Unload, Delete, Copy, Seed
		 * todo: add support for names longer than one string
		 */
		/*if (!(args.length < 1)) {
			msg.args(sender, 1);
			return true;
		}*/
		
		if (args.length == 2) {
			if (args[0].equalsIgnoreCase("unload")) {
				if (!sender.hasPermission("server.worlds.unload")) {
					msg.deny(sender);
					return true;
				}
				
				World world = Bukkit.getWorld(args[1]);
				
				if (worlds.isWorld(world)) {
					worlds.unloadWorld(world);
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world " + color.GOLD + args[1] + color.RED + " was not found!"));
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("delete")) {
				if (!sender.hasPermission("server.worlds.delete")) {
					msg.deny(sender);
					return true;
				}
				
				World world = Bukkit.getWorld(args[1]);
				
				if (worlds.isWorld(world)) {
					worlds.deleteWorld(world);
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world " + color.GOLD + args[1] + color.RED + " was not found!"));
					return true;
				}
			}
			
			if (args[0].equalsIgnoreCase("copy")) {
				if (!sender.hasPermission("server.worlds.copy")) {
					msg.deny(sender);
					return true;
				}
				
				World world = Bukkit.getWorld(args[1]);
				
				if (worlds.isWorld(world)) {
					worlds.copyWorld(args[1]);
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world " + color.GOLD + args[1] + color.RED + " was not found!"));
					return true;
				}
			}
		}
		return false;
	}
}