package net.sparkzz.servercontrol.commands;

import net.sparkzz.servercontrol.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.Set;

/**
 * Created by Brendon on 7/16/2014.
 */
public class OpsCommand extends CommandManager {

	public OpsCommand() {
		super("server.ops", "/ops");
	}

	@Override
	public boolean command(CommandSender sender, Command cmd, String[] args) {
		if (cmd.getName().equalsIgnoreCase("ops")) {
			if (args.length == 0) {
				if (getOpList().isEmpty()) {
					sender.sendMessage(msg.warn("Error retrieving operator list!"));
					return true;
				}
				sender.sendMessage(color.GRAY + "Admins " + getOpList());
				return true;
			}
		}
		return false;
	}

	private String getOpList() {
		StringBuilder playerList = new StringBuilder();
		Set<OfflinePlayer> players = Bukkit.getOperators();

		for (OfflinePlayer player : players) {
			if (playerList.length() > 0) {
				playerList.append(color.WHITE);
				playerList.append(", ");
			}

			playerList.append(player.isOnline() ? color.RED : color.GRAY);
			playerList.append(player.getName());
		}
		return color.DARK_GRAY + "(" + color.PINK + players.size() + color.DARK_GRAY + "): " + playerList.toString();
	}
}