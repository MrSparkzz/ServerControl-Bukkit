package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.players.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Brendon on 7/16/2014.
 */
public class MessageCommand extends CommandManager {

	private User user;

	public MessageCommand() {
		super("server.message", "/message [PLAYER] [MESSAGE..]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length < 2) {
			msg.args(sender, -1);
			return false;
		}

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			msg.noTarget(sender, args[0]);
			return true;
		}

		String message = msg.buildString(1, args);

		msg.send(sender, color.GREEN + "[Me -> " + color.GOLD + target.getDisplayName() + color.GREEN + "] " + color.RESET + message);

		if (sender instanceof Player) {
			Player player = (Player) sender;

			msg.send(target, color.GREEN + "[" + color.GOLD + ((Player) sender).getDisplayName() + color.GREEN + " -> Me] " + color.RESET + message);
			user.setLastMSG(player, target);
			return true;
		} else msg.send(target, color.GREEN + "[" + color.GOLD + "Console" + color.GREEN + " -> Me] " + color.RESET + message);
		return true;
	}
}