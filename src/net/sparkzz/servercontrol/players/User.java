package net.sparkzz.servercontrol.players;

import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Brendon on 7/15/2014.
 */
public class User extends UserData {

	private boolean invsee = false;
	private Player player, lastMSG;
	private String name, nickname;
	private UUID uuid;

	public User(Player player) {
		this.player = player;
		name = player.getName();
		uuid = player.getUniqueId();

		users.add(this);
	}

	public boolean hasLastMSG() {
		if (lastMSG != null) return true;
		else return false;
	}

	public boolean isInvsee() {
		return invsee;
	}

	public void setInvsee(boolean bool) {
		invsee = bool;
	}

	public String getName() {
		return player.getName();
	}

	public Player getPlayer() {
		return player;
	}

	public static User getUser(String name) {
		if (users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);
				
				if (user.getName().equalsIgnoreCase(name))
					return user;
			}
		}
		return null;
	}
	
	public static User getUser(Player player) {
		if (users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);

				if (user.getPlayer() == player)
					return user;
			}
		}
		return null;
	}
	
	public static User getUser(UUID uuid) {
		if (users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				User user = users.get(i);

				if (user.getUUID() == uuid);
					return user;
			}
		}
		return null;
	}

	public UUID getUUID() {
		return uuid;
	}

	public Player getLastMSG() {
		return lastMSG;
	}

	public void setLastMSG(Player player) {
		lastMSG = player;
	}
}