package net.sparkzz.servercontrol.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by Brendon on 7/3/2014.
 */
public class MsgHandler {

	private static MsgHandler instance = new MsgHandler();

	private Colorizer color = Colorizer.getColor();

	public static MsgHandler getHandler() {
		return instance;
	}

	public String buildString(int start, String[] args) {
		StringBuilder str = new StringBuilder();

		for (int i = start; i < args.length; i++) {
			str.append(args[i] + " ");
		}

		return color.translateColorCodes(str.toString());
	}

	public String info(String message) {
		return color.INFO + message;
	}

	public String severe(String message) {
		return color.SEVERE + message;
	}

	public String warn(String message) {
		return color.RED + message;
	}

	public void args(CommandSender sender, int caseNum) {
		switch (caseNum) {
			case -1:
				sender.sendMessage(color.RED + "Too few arguments!");
				break;
			case 1:
				sender.sendMessage(color.RED + "Too many arguments!");
				break;
			default:
				sender.sendMessage(color.RED + "Invalid arguments!");
				break;
		}
	}

	public void broadcast(String message) {
		Bukkit.broadcastMessage(color.translateColorCodes(message));
	}

	public void deny(CommandSender sender) {
		sender.sendMessage(color.RED + "You are not permitted to perform this action!");
	}

	public void massSend(String[] playerNameList, String message) {
		Player[] players = null;

		for (Player player : Bukkit.getOnlinePlayers()) {
			String name = player.getName();
			send(playerNameList[name.indexOf(name)], color.translateColorCodes(message));
		}
	}

	public void noTarget(CommandSender sender, String targetName) {
		sender.sendMessage(color.RED + "Specified player " + color.GOLD + targetName + color.RED + " is not online!");
	}

	public void noTarget(CommandSender sender, UUID targetUUID) {
		sender.sendMessage(color.RED + "Specified player with the UUID of " + color.GOLD + targetUUID + color.RED + " is not online!");
	}

	public void send(CommandSender sender, String message) {
		Player player = Bukkit.getPlayer(sender.getName());

		send(player, message);
	}

	public void send(Player player, String message) {
		if (player != null) player.sendMessage(color.translateColorCodes(message));
	}

	public void send(String name, String message) {
		Player player = Bukkit.getPlayer(name);

		send(player, message);
	}

	public void send(UUID id, String message) {
		Player player = Bukkit.getPlayer(id);

		send(player, message);
	}

	public void sendRaw(CommandSender sender, String message) {
		Player player = Bukkit.getPlayer(sender.getName());

		sendRaw(player, message);
	}

	public void sendRaw(Player player, String message) {
		if (player != null) send(player, color.deColorize(message));
	}

	public void sendRaw(String name, String message) {
		Player player = Bukkit.getPlayer(name);

		sendRaw(player, message);
	}

	public void sendRaw(UUID id, String message) {
		Player player = Bukkit.getPlayer(id);

		sendRaw(player, message);
	}
}