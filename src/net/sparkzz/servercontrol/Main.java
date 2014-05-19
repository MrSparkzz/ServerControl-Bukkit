package net.sparkzz.servercontrol;

import net.sparkzz.servercontrol.command.Commands;
import net.sparkzz.servercontrol.event.ChatListener;
import net.sparkzz.servercontrol.event.Events;
import net.sparkzz.servercontrol.event.SwearListener;
import net.sparkzz.util.FileManager;
import net.sparkzz.util.LogHandler;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	LogHandler logger = LogHandler.getInstance();
	SwearListener swear = SwearListener.getInstance();
	Commands commands = new Commands();
	Events events = new Events();

	public static FileManager file = new FileManager();
	
	public static FileManager getManager() {
		return file;
	}
	
	@Override
	public void onDisable() {
		saveDefaultConfig();
		
		logger.info(this, "Disabled " + this.getName());
	}
	
	@Override
	public void onEnable() {
		commands.initCommands();
		events.registerEvents(this);
		
		boolean swearProtect = this.getConfig().getBoolean("messaging.clean.enabled");
		boolean toLower = this.getConfig().getBoolean("messaging.lower.enabled");
		
		if (swearProtect || (swearProtect && toLower)) {
			Events.registerEvents(new SwearListener(), this);
		} else if (toLower) {
			Events.registerEvents(new ChatListener(), this);
		}
		
		file.setup(this);
		file.createYML(this, "players");
		
		if (this.getConfig().getBoolean("messaging.clean.enabled"))
			swear.createSwearList();
		
		logger.info(this, "Enabled " + this.getName());
	}
	
	static Main instance = new Main();
	
	public static Main getInstance() {
		return instance;
	}
}