package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Brendon on 7/16/2014.
 */
public class WhisperCommand extends CommandManager {

	public WhisperCommand() {
		super("server.whisper", "/whisper [PLAYER] [MESSAGE..]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, msg.warn("Only players can use this command!"));
			return true;
		}

		if (args.length < 2) {
			msg.args(sender, -1);
			return false;
		}

		Player player = (Player) sender;

		Player target = Bukkit.getPlayer(args[0]);

		if (target == null) {
			msg.noTarget(sender, args[0]);
			return true;
		}

		String message = msg.buildString(1, args);

		msg.send(player, msg.info("Me: " + color.deColorize(message)));
		msg.send(target, msg.info(player.getName() + ": " + color.deColorize(message)));
		return true;
	}
}