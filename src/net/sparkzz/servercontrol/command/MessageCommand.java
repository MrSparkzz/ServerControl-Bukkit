package net.sparkzz.servercontrol.command;

import net.sparkzz.util.Colorizer;
import net.sparkzz.util.MsgHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

	MsgHandler msg = MsgHandler.getInstance();
	Colorizer color = Colorizer.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, color.RED + "Only players can use this command!");
			return true;
		}
		
		if(!sender.hasPermission("server.msg")) {
			msg.deny(sender);
			return true;
		}
		                                                                                                                                                                      
		if (args.length < 2) {
			msg.args(sender, 1);
			return true;
		}
		
		Player player = (Player) sender;
		
		Player target = Bukkit.getPlayer(args[0]);
		
		if (target == null) {
			msg.send(sender, color.RED + "Specified player " + args[0] + " is not online!");
			return true;
		}
		
		String message = msg.buildString(1, args);
		
		msg.send(player, color.GRAY + "[Me -> " + color.GOLD + target.getDisplayName() + color.GRAY + "] " + color.RESET + message);
		
		if (sender instanceof Player)
			msg.send(target, color.GRAY + "[" + color.GOLD + player.getDisplayName() + color.GRAY + " -> Me] " + color.RESET + message);
		if (!(sender instanceof Player))
			msg.send(player, color.GRAY + "[" + color.GOLD + "Console" + color.GRAY + " -> Me] " + color.RESET + message);
		
		return true;
	}
}
