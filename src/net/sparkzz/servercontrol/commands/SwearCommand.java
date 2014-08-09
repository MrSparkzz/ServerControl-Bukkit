package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.event.Events;
import net.sparkzz.servercontrol.event.SwearListener;
import net.sparkzz.servercontrol.util.Options;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Brendon on 7/16/2014.
 */
public class SwearCommand extends CommandManager {

	private Options options;
	private SwearListener swear = SwearListener.getInstance();

	public SwearCommand() {
		super("server.swear", "/swear <add/remove/enable/disable/save/reload> <swear/replacement/[SWEAR]> [SWEAR/REPLACEMENT] [SWEAR]");
	}

	//TODO: add the ability to change modes in game | possibly make a new custom config for swears

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length == 0) {
			msg.args(sender, -1);
			return false;
		}

		boolean swearProtection = options.getOption(Options.SWEAR_PROTECT);

		if (args.length == 1) {
			if (args[0].equalsIgnoreCase("add")) {
				msg.args(sender, -1);
				return false;
			}

			if (args[0].equalsIgnoreCase("disable")) {
				if (!sender.hasPermission("server.swear.disable")) {
					msg.deny(sender);
					return true;
				}

				if (swearProtection) {
					swear.clearSwears();
					AsyncPlayerChatEvent.getHandlerList().unregister(swear);
					options.setOption(Options.SWEAR_PROTECT, false);
					msg.send(sender, color.YELLOW + "Swear protection has been disabled. Swears will now show up in chat!");
				}

				return true;
			}

			if (args[0].equalsIgnoreCase("enable")) {
				if (!sender.hasPermission("server.swear.enable")) {
					msg.deny(sender);
					return true;
				}

				if (!swearProtection) {
					Events.registerEvent(new SwearListener());
					swear.createSwearList();
					options.setOption(Options.SWEAR_PROTECT, true);
					msg.send(sender, color.GREEN + "Swear protection has been enabled.");
				} else {
					msg.send(sender, msg.warn("Swear protection is already enabled."));
				}

				return true;
			}

			if (args[0].equalsIgnoreCase("mode")) {
				if (!sender.hasPermission("server.swear.mode")) {
					msg.deny(sender);
					return true;
				}

				msg.args(sender, -1);
				return false;
			}

			if (args[0].equalsIgnoreCase("reload")) {
				if (!sender.hasPermission("server.swear.reload")) {
					msg.deny(sender);
					return true;
				}

				if (swearProtection) {
					swear.reloadSwears();
					msg.send(sender, color.GREEN + "The swear list has been reloaded.");
				} else {
					msg.send(sender, msg.warn("Swear protection is not enabled."));
					msg.send(sender, msg.info("Use '/swear enable' to enable swear protection."));
				}

				return true;
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (!sender.hasPermission("server.swear.remove")) {
					msg.deny(sender);
					return true;
				}

				msg.args(sender, -1);
				return false;
			}

			if (args[0].equalsIgnoreCase("save")) {
				if (!sender.hasPermission("server.swear.save")) {
					msg.deny(sender);
					return true;
				}

				files.getConfig().set("swears", swear.getSwearList());
				msg.send(sender, color.CYAN + "Swears successfully saved to file!");
				return true;
			}
		}

		if (args.length > 1) {
			if (args[0].equalsIgnoreCase("add")) {
				if (!sender.hasPermission("server.swear.add")) {
					msg.deny(sender);
					return true;
				}

				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("swear")) {
						msg.args(sender, -1);
						return true;
					} else if (args[1].equalsIgnoreCase("replacement")) {
						msg.args(sender, -1);
						return true;
					}

					swear.addSwear(args[2], "");
					msg.send(sender, color.GREEN + "You have successfully added: " + color.GOLD + args[2] + color.GREEN + " to the swear list.");
					return true;
				}

				if (args.length == 3) {
					if (args[1].equalsIgnoreCase("swear")) {
						swear.addSwear(args[2].toLowerCase(), "");
						msg.send(sender, color.GREEN + "You have successfully added: " + color.GOLD + args[2] + color.GREEN + " to the swear list.");
						return true;
					}

					swear.addSwear(args[1].toLowerCase(), args[2].toLowerCase());
					msg.send(sender, color.GREEN + "You have successfully added: " + color.GOLD + args[1] + color.GREEN + " and its replacement: " + color.GOLD + args[2] + color.GREEN + " to the swear list.");
					return true;
				}

				if (args.length == 4) {
					if (args[1].equalsIgnoreCase("replacement")) {
						swear.addSwear(args[3].toLowerCase(), args[2].toLowerCase());
						msg.send(sender, color.GREEN + "You have successfully added: " + color.GOLD + args[2] + color.GREEN + " and its replacement: " + color.GOLD + args[3] + color.GREEN + " to the swear list.");
					} else {
						msg.args(sender, 0);
						return false;
					}

					return true;
				}
			}

			if (args[0].equalsIgnoreCase("mode")) {
				if (!sender.hasPermission("server.swear.mode")) {
					msg.deny(sender);
					return true;
				}

				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("0")) {
						if (swear.getMode() == 0) {
							msg.send(sender, msg.warn("The swear mode is already set to 0"));
							return true;
						}

						swear.setMode(0);
						msg.send(sender, color.GREEN + "The swear mode has been set to 0");
						return true;
					}

					if (args[1].equalsIgnoreCase("1")) {
						if (swear.getMode() == 1) {
							msg.send(sender, msg.warn("The swear mode is already set to 1"));
							return true;
						}

						swear.setMode(1);
						msg.send(sender, color.GREEN + "The swear mode has been set to 1");
						return true;
					}

					if (args[1].equalsIgnoreCase("2")) {
						if (swear.getMode() == 2) {
							msg.send(sender, msg.warn("The swear mode is already set to 2"));
							return true;
						}

						swear.setMode(2);
						msg.send(sender, color.GREEN + "The swear mode has been set to 2");
						return true;
 					}
				}
			}

			if (args[0].equalsIgnoreCase("remove")) {
				if (!sender.hasPermission("server.swear.remove")) {
					msg.deny(sender);
					return true;
				}

				if (args.length == 2) {
					if (args[1].equalsIgnoreCase("swear")) {
						msg.args(sender, -1);
						return true;
					} else if (args[1].equalsIgnoreCase("replacement")) {
						msg.args(sender, -1);
						return true;
					}

					boolean removed = swear.removeSwear(args[2].toLowerCase());

					if (removed) msg.send(sender, color.GREEN + "You have successfully removed: " + color.GOLD + args[2] + color.GREEN + " from the swear list.");
					else msg.send(sender, msg.warn("The swear: " + color.GOLD + args[2] + color.RED + " is not in the list!"));
					return true;
				}
			}
		}

		return false;
	}
}