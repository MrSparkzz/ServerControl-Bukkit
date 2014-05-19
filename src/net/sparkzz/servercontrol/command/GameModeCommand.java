package net.sparkzz.servercontrol.command;

import net.sparkzz.util.Colorizer;
import net.sparkzz.util.MsgHandler;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GameModeCommand implements CommandExecutor {

	static MsgHandler msg = MsgHandler.getInstance();
	static Colorizer color = Colorizer.getInstance();
	
	static String gm_surv = color.GREEN + "You are now in Survival Mode!";
	static String gm_create = color.GREEN + "You are now in Creative Mode!";
	static String gm_advent = color.GREEN + "You are now in Adventure Mode!";
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// /gamemode (no args)
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				msg.send(sender, color.RED + "Silly console, you're not a player!");
				return true;
			}
			
			if (!(sender.hasPermission("server.gamemode.self"))) {
				msg.deny(sender);
				return true;
			}
			
			Player player = (Player) sender;

			GameMode gamemode = player.getGameMode();
			
			switch(gamemode) {
				case SURVIVAL:
					player.setGameMode(GameMode.CREATIVE);
					msg.send(sender, gm_create);
					break;
				case CREATIVE:
					player.setGameMode(GameMode.SURVIVAL);
					msg.send(sender, gm_surv);
					break;
				case ADVENTURE:
					player.setGameMode(GameMode.SURVIVAL);
					msg.send(sender, gm_surv);
					break;
			}
			return true;
		}
		
		// gamemode 1, 2, 3 (self)
		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				msg.send(sender, color.RED + "Silly console, you're not a player!");
				return true;
			}
			
			if (!(sender.hasPermission("server.gamemode.self"))) {
				msg.deny(sender);
				return true;
			}
			
			String x = args[0];
			Player player = (Player) sender;
			
			gmSwitch(player, x);
			
			return true;
		}
		
		// gamemode player 1, 2, 3 (another player)
		if (args.length == 2) {
			String x = args[1];
			Player target = Bukkit.getPlayer(args[0]);
			
			if (!(sender.hasPermission("server.gamemode.others"))) {
				msg.deny(sender);
				return true;
			}
			
			if (target == null) {
				msg.targetNotFound(sender, args[0]);
				return true;
			}
			
			gmSwitch(target, x);
			
			return true;
		}
		return false;
	}

	// switch on GameMode
	public static void gmSwitch(Player player, String x) {
		int y = -1;
		
		if (x.equalsIgnoreCase("survival") || x.equalsIgnoreCase("s") || x.equalsIgnoreCase("0"))
			y = 0;
		
		if (x.equalsIgnoreCase("creative") || x.equalsIgnoreCase("c") || x.equalsIgnoreCase("1"))
			y = 1;
		
		if (x.equalsIgnoreCase("adventure") || x.equalsIgnoreCase("a") || x.equalsIgnoreCase("2"))
			y = 2;
		
		switch (y) {
			case 0:
				player.setGameMode(GameMode.SURVIVAL);
				msg.send(player, gm_surv);
				break;
			case 1:
				player.setGameMode(GameMode.CREATIVE);
				msg.send(player, gm_create);
				break;
			case 2:
				player.setGameMode(GameMode.ADVENTURE);
				msg.send(player, gm_advent);
				break;
			default:
				break;
		}
	}
}