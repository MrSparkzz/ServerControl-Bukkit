package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Brendon on 7/16/2014.
 */
public class GameModeCommand extends CommandManager {

	private String survival = color.GREEN + "You are now in Survival Mode!";
	private String creative = color.GREEN + "You are now in Creative Mode!";
	private String adventure = color.GREEN + "You are now in Adventure Mode!";

	public GameModeCommand() {
		super("server.gamemode", "/gamemode <adventure/creative/survival/[NUMBER]> [PLAYER]");
	}

	@Override
	public boolean command(CommandSender sender, Command command, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				msg.send(sender, msg.warn("Silly console, you're not a player!"));
				return true;
			}

			if (!sender.hasPermission("server.gamemode.self")) {
				msg.deny(sender);
				return true;
			}

			Player player = (Player) sender;

			GameMode gamemode = player.getGameMode();

			switcher(player, gamemode);
			return true;
		}

		if (args.length == 1) {
			if (!(sender instanceof Player)) {
				msg.send(sender, msg.warn("Silly console, you're not a player!"));
				return true;
			}

			if (!sender.hasPermission("sender.gamemode.self")) {
				msg.deny(sender);
				return true;
			}

			Player player = (Player) sender;

			GameMode current = getGameMode(args[0]);

			if (current != null)
				switcherV2(player, current);
			else {
				msg.send(sender, msg.warn("Invalid option!"));
				return false;
			}
			return true;
		}

		if (args.length == 2) {
			if (!sender.hasPermission("sender.gamemode.others")) {
				msg.deny(sender);
				return true;
			}

			Player target = Bukkit.getPlayer(args[1]);

			if (target == null) {
				msg.noTarget(sender, args[1]);
				return true;
			}

			GameMode current = getGameMode(args[0]);

			if (current != null)
				switcherV2(target, current);
			else {
				msg.send(sender, msg.warn("Invalid option!"));
				return false;
			}
			return true;
		}

		if (args.length > 2) {
			msg.args(sender, 1);
			return false;
		}
		return false;
	}

	// get GameMode
	private GameMode getGameMode(String input) {
		if (input.equalsIgnoreCase("survival") || input.equalsIgnoreCase("surv") || input.equalsIgnoreCase("s") || input.equalsIgnoreCase("0"))
			return GameMode.SURVIVAL;
		if (input.equalsIgnoreCase("creative") || input.equalsIgnoreCase("create") || input.equalsIgnoreCase("c") || input.equalsIgnoreCase("1"))
			return GameMode.CREATIVE;
		if (input.equalsIgnoreCase("adventure") || input.equalsIgnoreCase("adv") || input.equalsIgnoreCase("a") || input.equalsIgnoreCase("2"))
			return GameMode.ADVENTURE;
		return null;
	}

	// switch GameMode
	private void switcher(Player player, GameMode gamemode) {
		switch (gamemode) {
			case SURVIVAL:
				player.setGameMode(GameMode.CREATIVE);
				msg.send(player, creative);
				break;
			case CREATIVE:
				player.setGameMode(GameMode.SURVIVAL);
				msg.send(player, survival);
				break;
			case ADVENTURE:
				player.setGameMode(GameMode.SURVIVAL);
				msg.send(player, survival);
				break;
		}
	}

	// switch GameMode
	private void switcherV2(Player player, GameMode gamemode) {
		int x = 0;

		if (gamemode.equals(GameMode.SURVIVAL)) x = 0;
		if (gamemode.equals(GameMode.CREATIVE)) x = 1;
		if (gamemode.equals(GameMode.ADVENTURE)) x = 2;

		switch (x) {
			case 0:
				player.setGameMode(GameMode.CREATIVE);
				msg.send(player, creative);
				break;
			case 1:
				player.setGameMode(GameMode.ADVENTURE);
				msg.send(player, adventure);
				break;
			case 2:
				player.setGameMode(GameMode.SURVIVAL);
				msg.send(player, survival);
				break;
		}
	}
}