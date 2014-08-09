package net.sparkzz.servercontrol;

import net.sparkzz.servercontrol.command.Commands;
import net.sparkzz.servercontrol.event.Events;
import net.sparkzz.servercontrol.event.SwearListener;
import net.sparkzz.servercontrol.players.User;
import net.sparkzz.servercontrol.players.UserData;
import net.sparkzz.servercontrol.util.FileManager;
import net.sparkzz.servercontrol.util.LogHandler;
import net.sparkzz.servercontrol.util.Options;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static FileManager files;
	private static LogHandler logger;
	private Options option;
	private PluginDescriptionFile pdFile = this.getDescription();

	@Override
	public void onDisable() {
		Events.unRegisterEvents();

		UserData.users = null;

		logger.info(pdFile.getName() + " v" + pdFile.getVersion() + " has been disabled");
	}

	@Override
	public void onEnable() {
		files.setup(this);
		files.createDataFile();
		files.createPlayerConfig();

		option.setupOptions();

		Commands.initCommands();
		Events.registerEvents(this);

		if (option.getOption(Options.SWEAR_PROTECT))
			Events.registerEvent(new SwearListener());

		if (Bukkit.getOnlinePlayers().length > 0)
			User.rebuild();

		logger.info(pdFile.getName() + " v" + pdFile.getVersion() + " has been enabled");
	}
}