package net.sparkzz.servercontrol.command;

import net.sparkzz.servercontrol.player.User;
import net.sparkzz.util.MsgHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvseeCommand implements CommandExecutor {

	MsgHandler msg = MsgHandler.getInstance();
	User user = User.getUser();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, msg.warn("Only players can use this command!"));
			return true;
		}
		
		/*if (!sender.hasPermission("server.invsee.peek") || !sender.hasPermission("server.invsee.modify")) {
			msg.deny(sender);
			return true;
		}*/
		
		if (args.length < 1) {
			msg.args(sender, 1);
			return true;
		}
		
		if (args.length > 1) {
			msg.args(sender, 2);
			return true;
		}
		
		if (args.length == 1) {
			
			Player player = (Player) sender;
			Player target = Bukkit.getPlayer(args[0]);
			
			if (target == null) {
				msg.targetNotFound(sender, args[0]);
				return true;
			}
			
			Inventory inv = target.getInventory();
			
			player.closeInventory();
			player.openInventory(inv);
			user.setInvSee(true);
			return true;
		}
		return false;
	}
}