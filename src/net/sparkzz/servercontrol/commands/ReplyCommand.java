package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.players.User;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Brendon on 7/16/2014.
 */
public class ReplyCommand extends CommandManager {

	public ReplyCommand() {
		super("server.reply", "/reply [MESSAGE..]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, msg.warn("Only players can use this command!"));
			return true;
		}

		Player player = (Player) sender;
		User user = User.getUser(player);

		if (!user.hasLastMSG()) {
			msg.send(player, msg.warn("There is no one to reply to."));
			msg.send(player, msg.info("To send a message use /message"));
			return true;
		}

		Player target = user.getLastMSG();
		User targetUser = User.getUser(target);

		if (targetUser == null) {
			msg.send(player, msg.warn("The specified player: " + color.GOLD + target.getName() + color.RED + " could not be found!"));
			return true;
		}

		String message = msg.buildString(0, args);

		msg.send(player, color.GREEN + "[Me -> " + color.GOLD + target.getDisplayName() + color.GREEN + "] " + color.RESET + message);
		msg.send(target, color.GREEN + "[" + color.GOLD + player.getDisplayName() + color.GREEN + " -> Me] " + color.RESET + message);
		targetUser.setLastMSG(player);
		return false;
	}
}