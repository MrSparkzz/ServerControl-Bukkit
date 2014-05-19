package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.Main;
import net.sparkzz.event.Manager;
import net.sparkzz.util.FileManager;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Events {

	Main main = Main.getInstance();
	static Manager manager = Manager.getManager();
	FileManager file = Main.getManager();
	
	public void registerEvents(Plugin plugin) {
		manager.registerEvent(new AttendanceListener(), plugin);
		manager.registerEvent(new PlayerListener(), plugin);
	}
	
	public static void registerEvents(Listener listener, Plugin plugin) {
		manager.registerEvent(listener, plugin);
	}
}