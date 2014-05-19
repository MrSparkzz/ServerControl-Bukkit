package net.sparkzz.servercontrol.command;

import net.sparkzz.util.MsgHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhisperCommand implements CommandExecutor {

	MsgHandler msg = MsgHandler.getInstance();
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			msg.send(sender, msg.warn("Only players can use this command!"));
			return true;
		}
		
		if(!sender.hasPermission("server.whisper")) {
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
			msg.targetNotFound(sender, args[0]);
			return true;
		}
		
		String message = msg.buildString(1, args);
		
		msg.sendRaw(player, msg.info("Me: " + message));
		msg.sendRaw(target, msg.info(player.getName() + ": " + message));
		
		return true;
	}
}