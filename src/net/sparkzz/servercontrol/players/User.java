package net.sparkzz.servercontrol.players;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Brendon on 7/15/2014.
 */
public class User {

	private static HashMap<Player, Player> lastMSG = new HashMap<Player, Player>();
	private static List<Player> invsee = new ArrayList<Player>();

	public static void setInvsee(Player player, boolean value) {
		if (value) invsee.add(player);
		else invsee.remove(player);
	}

	public static boolean isInvsee(Player player) {
		if (invsee.contains(player)) return true;
		else return false;
	}

	public static void setLastMSG(Player sender, Player target) {
		lastMSG.put(target, sender);
	}

	public static boolean hasLastMSG(Player player) {
		if (lastMSG.containsKey(player)) return true;
		else return false;
	}

	public static Player getLastMSG(Player player) {
		return lastMSG.get(player);
	}
}