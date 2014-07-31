package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.util.Options;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Created by Brendon on 7/16/2014.
 */
public class MOTDCommand extends CommandManager {

	private Options options;
	private String ingameMOTD = options.getMessage(Options.MOTD_INGAME),
			serverMOTD = options.getMessage(Options.MOTD_SERVER);

	public MOTDCommand() {
		super("server.motd", "/motd <broadcast/view/set> <ingame/server/[MESSAGE..]> [MESSAGE..]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("broadcast")) {
				if (!sender.hasPermission("server.motd.broadcast")) {
					msg.deny(sender);
					return true;
				}

				msg.broadcast(ingameMOTD);
				return true;
			}

			if (args[0].equalsIgnoreCase("set")) {
				if (!sender.hasPermission("server.motd.set")) {
					msg.deny(sender);
					return true;
				}
				return false;
			}

			if (args[0].equalsIgnoreCase("view")) {
				if (!sender.hasPermission("server.motd.view")) {
					msg.deny(sender);
					return true;
				}

				msg.send(sender, ingameMOTD);
				return true;
			}
		}

		if (args.length > 1) {
			if (args[0].equalsIgnoreCase("set")) {
				int start = 1;

				Options option = Options.MOTD_INGAME;
				String setter = "motd.ingame", permission = "server.motd.set.ingame";

				if (args[1].equalsIgnoreCase("server")) {
					option = Options.MOTD_SERVER;
					permission = "server.motd.set.server";
					setter = "motd.server";
					start = 2;
				} else if (args[1].equalsIgnoreCase("ingame")) {
					start = 2;
				}

				if (!sender.hasPermission(permission)) {
					msg.deny(sender);
					return true;
				}

				if (!(args[start] == null)) {
					String message = msg.buildString(start, args);

					options.setMessage(option, message);

					files.getConfig().set(setter, message);
					files.saveConfig();
					return true;
				}
			}

			if (args[0].equalsIgnoreCase("view")) {
				Options option = Options.MOTD_INGAME;
				String permission;

				if (args[1].equalsIgnoreCase("ingame"))
					permission = "server.motd.view.ingame";
				else if (args[1].equalsIgnoreCase("server")) {
					option = Options.MOTD_SERVER;
					permission = "server.motd.view.server";
				} else {
					msg.args(sender, 0);
					return false;
				}

				if (!sender.hasPermission(permission)) {
					msg.deny(sender);
					return true;
				}

				msg.send(sender, options.getMessage(option));
				return true;
			}
			return false;
		}

		if (args.length == 0) {
			if (!sender.hasPermission("server.motd.view.ingame")) {
				msg.deny(sender);
				return true;
			}

			msg.send(sender, ingameMOTD);
			return true;
		}
		return false;
	}
}