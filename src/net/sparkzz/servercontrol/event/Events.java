package net.sparkzz.servercontrol.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 * Created by Brendon on 7/3/2014.
 */
public class Events {

	private static Plugin plugin = null;

	public static void registerEvents(Plugin main) {
		plugin = main;

		registerEvent(new AttendanceListener());
		registerEvent(new ChatListener());
		registerEvent(new PlayerListener());
	}

	public static void registerEvent(Listener listener) {
		Bukkit.getPluginManager().registerEvents(listener, plugin);
	}

	public static void unRegisterEvents() {
		//  AttendanceListener class
		PlayerJoinEvent.getHandlerList().unregister(plugin);
		PlayerLoginEvent.getHandlerList().unregister(plugin);
		PlayerQuitEvent.getHandlerList().unregister(plugin);

		// ChatListener/SwearListener class
		AsyncPlayerChatEvent.getHandlerList().unregister(plugin);

		// PlayerListener class
		InventoryClickEvent.getHandlerList().unregister(plugin);
		InventoryCloseEvent.getHandlerList().unregister(plugin);
	}
}