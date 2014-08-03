package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import net.sparkzz.servercontrol.players.User;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Created by Brendon on 7/16/2014.
 */
public class InvseeCommand extends CommandManager {

	public InvseeCommand() {
		super("server.invsee", "/invsee [PLAYER]");
	}

	//TODO: More debugging in the invsee feature

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, msg.warn("Only players can use this command!"));
			return true;
		}

		if (!sender.hasPermission("server.invsee.peek") || !sender.hasPermission("server.invsee.modify")) {
			msg.deny(sender);
			return true;
		}

		if (args.length < 1) {
			msg.args(sender, -1);
			return false;
		}

		if (args.length > 1) {
			msg.args(sender, 1);
			return false;
		}

		if (args.length == 1) {
			Player player = (Player) sender;
			User user = User.getUser(player);
			Player target = Bukkit.getPlayer(args[0]);

			if (target == null) {
				msg.noTarget(sender, args[0]);
				return true;
			}

			Inventory inv = target.getInventory();

			player.closeInventory();
			player.openInventory(inv);
			user.setInvsee(true);
			return true;
		}
		return false;
	}
}