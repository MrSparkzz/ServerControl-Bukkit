package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.util.Options;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Brendon on 7/3/2014.
 */
public class ChatListener implements Listener {

	private Options options;

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onChat(AsyncPlayerChatEvent event) {
		if (options.getOption(Options.LOWERCASE_CHAT)) {
			String message = event.getMessage().toLowerCase();

			event.setMessage(message);
		}

		// TODO: add admin only chat support
	}
}