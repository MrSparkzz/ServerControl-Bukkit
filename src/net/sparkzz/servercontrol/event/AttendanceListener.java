package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.players.User;
import net.sparkzz.servercontrol.util.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;

/**
 * Created by Brendon on 7/15/2014.
 */
public class AttendanceListener implements Listener {

	private static Colorizer color;
	private static FileManager files;
	private static LogHandler logger;
	private static MsgHandler msg;
	private static Options options;
	private static User user;

	/*
	 * when a player successfully joins the server
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		User user = new User(player);

		String playerUUID = user.getUUID().toString();

		if (!player.hasPlayedBefore()) {
			files.getConfig().set("players.count", files.getConfig().getInt("players.count") + 1);
			files.saveConfig();
		}

		if (!files.getPlayerConfig().contains(playerUUID)) {
			setupPlayerData(player);
			files.savePlayers();
		} else {
			files.getPlayerConfig().set(playerUUID + ".previous_username", files.getPlayerConfig().get(playerUUID + "username"));
			files.getPlayerConfig().set(playerUUID + ".username", player.getName());

			int joins = files.getPlayerConfig().getInt(playerUUID + ".joins");

			files.getPlayerConfig().set(playerUUID + ".joins", joins + 1);
			files.savePlayers();
		}

		String motd = options.getMessage(Options.MOTD_INGAME),
				joinMessage = options.getMessage(Options.JOIN_MESSAGE);

		if (!joinMessage.equals("")) event.setJoinMessage(color.translateColorCodes(joinMessage.replace("[PLAYER]", player.getDisplayName())));
		else event.setJoinMessage(null);

		if (!motd.equals(""))
			msg.send(player, motd.replace("[SERVER]", Options.getMessage(Options.SERVER_NAME)));

		//logger.info(user.getName() + "'s session ID is: " + user.getSessionID());
	}

	/*
	 * when a player presses the join button on the server
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLogin(PlayerLoginEvent event) {
		if (options.getOption(Options.ADMINS_ONLY)) {
			if (!event.getPlayer().isOp() || !event.getPlayer().hasPermission("server.adminsonly.bypass")) {
				event.disallow(PlayerLoginEvent.Result.KICK_OTHER, options.getMessage(Options.ADMINS_ONLY_KICK_MSG));
			}
		}
	}

	/*
	 * when a player leaves the server
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		User user = User.getUser(player);

		Location location = event.getPlayer().getLocation();
		String playerUUID = user.getUUID().toString();

		files.getPlayerConfig().set(playerUUID + ".location.world", location.getWorld().getName());
		files.getPlayerConfig().set(playerUUID + ".location.x", location.getX());
		files.getPlayerConfig().set(playerUUID + ".location.y", location.getY());
		files.getPlayerConfig().set(playerUUID + ".location.z", location.getZ());
		files.getPlayerConfig().set(playerUUID + ".location.pitch", location.getPitch());
		files.getPlayerConfig().set(playerUUID + ".location.yaw", location.getY());

		files.savePlayers();

		String quitMessage = options.getMessage(Options.QUIT_MESSAGE);

		if (!quitMessage.equals("")) event.setQuitMessage(color.translateColorCodes(quitMessage.replace("[PLAYER]", player.getDisplayName()))); // TODO: it seems like sometimes the quit message is the default
		else event.setQuitMessage(null);

		user.deleteUser();
	}

	private void setupPlayerData(Player player) {
		DecimalFormat decimal = new DecimalFormat("00000");

		String playerUUID = player.getUniqueId().toString();

		files.getPlayerConfig().set(playerUUID + ".username", player.getName());
		files.getPlayerConfig().set(playerUUID + ".previous_username", "");
		files.getPlayerConfig().set(playerUUID + ".nickname", "");
		files.getPlayerConfig().set(playerUUID + ".player_number", decimal.format(files.getConfig().getInt("players.id_base") + files.getConfig().getInt("players.count") + 1));
		files.getPlayerConfig().set(playerUUID + ".joins", 1);
		files.getPlayerConfig().set(playerUUID + ".location.world", player.getLocation().getWorld().getName());
		files.getPlayerConfig().set(playerUUID + ".location.x", player.getLocation().getX());
		files.getPlayerConfig().set(playerUUID + ".location.y", player.getLocation().getY());
		files.getPlayerConfig().set(playerUUID + ".location.z", player.getLocation().getZ());
		files.getPlayerConfig().set(playerUUID + ".location.pitch", player.getLocation().getPitch());
		files.getPlayerConfig().set(playerUUID + ".location.yaw", player.getLocation().getYaw());

		logger.info(player.getName() + " was added to players.yml!");
	}
}