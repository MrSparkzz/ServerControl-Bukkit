package net.sparkzz.servercontrol.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSwear(AsyncPlayerChatEvent event) {
		String message = event.getMessage().toLowerCase();
		
		event.setMessage(message);
	}
}