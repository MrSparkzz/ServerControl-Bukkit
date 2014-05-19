package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.Main;
import net.sparkzz.util.FileManager;
import net.sparkzz.util.LogHandler;
import net.sparkzz.util.MsgHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AttendanceListener implements Listener {

	static Main main = Main.getInstance();
	static MsgHandler msg = MsgHandler.getInstance();
	static LogHandler log = LogHandler.getInstance();
	static FileManager manager = Main.getManager();
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		int plays = 0;
		
		if (!player.hasPlayedBefore()) {
			try {
				manager.getData().set(player.getName() + ".joins", ++plays);
				manager.saveData();
			} catch (Exception e) {
				log.severe(main, "Problem adding new player " + player.getName() + " to players.yml");
			}
		} else {
			try {
				plays = manager.getData().getInt(player.getName() + ".joins");
				
				plays++;
				
				manager.getData().set(player.getName() + ".joins", plays);
				manager.saveData();
			} catch (Exception e) {
				log.severe(main, "Problem incrementing " + player.getName() + "'s play counter");
			}
		}
		
		String motd = manager.getConfig().getString("messages.motd");
		String joinmessage = manager.getConfig().getString("messages.join");

		if (joinmessage != null) event.setJoinMessage(msg.translateColor(joinmessage).replace("{PLAYER}", player.getDisplayName()));
		else event.setJoinMessage(null);
		
		if (motd != null)
			msg.send(player, motd);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		
		String quitmessage = manager.getConfig().getString("messages.quit");
		
		if (quitmessage != null) event.setQuitMessage(msg.translateColor(quitmessage).replace("{PLAYER}", player.getDisplayName()));
		else event.setQuitMessage(null);
	}
}
