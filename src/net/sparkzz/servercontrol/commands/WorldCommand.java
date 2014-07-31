package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.util.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Brendon on 7/16/2014.
 */
public class WorldCommand extends CommandManager {

	private WorldManager worlds;

	public WorldCommand() {
		super("server.world", "/world <copy/delete/seed/unload> [WORLD]");
	}

	//TODO: more debugging | new world command

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("seed")) {
				if (!(sender instanceof Player)) {
					msg.send(sender, msg.warn("Only players can perform this command!"));
					return true;
				}

				if (!sender.hasPermission("server.worlds.seed")) {
					msg.deny(sender);
					return true;
				}

				msg.send(sender, color.CYAN + "The seed for this world is: " + ((Player) sender).getWorld().getSeed());
				return true;
			}
		}

		if (args.length == 2) {
			if (args[0].equalsIgnoreCase(("copy"))) {
				if (!sender.hasPermission("server.worlds.copy")) {
					msg.deny(sender);
					return true;
				}

				World world = Bukkit.getWorld(args[1]);

				if (world != null) {
					worlds.copyWorld(args[1]);
					msg.send(sender, color.GOLD + world.getName() + color.GREEN + " has been copied successfully.");
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world " + color.GOLD + args[1] + color.RED + " could not be found!"));
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("delete")) {
				if (!sender.hasPermission("server.worlds.delete")) {
					msg.deny(sender);
					return true;
				}

				World world = Bukkit.getWorld(args[1]);

				if (world != null) {
					worlds.deleteWorld(world);
					msg.send(sender, color.GOLD + world.getName() + color.RED + " has been deleted!");
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world: " + color.GOLD + args[1] + color.RED + " could not be found!"));
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("seed")) {
				if (!sender.hasPermission("server.worlds.seed")) {
					msg.deny(sender);
					return true;
				}

				World world = Bukkit.getWorld(args[1]);

				if (world != null) {
					msg.send(sender, color.CYAN + "The seed for " + color.GOLD + world.getName() + color.CYAN + " is: " + color.GOLD + world.getSeed());
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world: " + color.GOLD + args[1] + color.RED + " could not be found!"));
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("unload")) {
				if (!sender.hasPermission("server.worlds.unload")) {
					msg.deny(sender);
					return true;
				}

				World world = Bukkit.getWorld(args[1]);

				if (world != null) {
					worlds.unloadWorld(world);
					msg.send(sender, color.GOLD + world.getName() + color.GREEN + " has been unloaded.");
					return true;
				} else {
					msg.send(sender, msg.warn("Specified world: " + color.GOLD + args[1] + color.RED + " could not be found!"));
					return true;
				}
			}


		}
		return false;
	}
}