package net.sparkzz.servercontrol.util;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

/**
 * Created by Brendon on 7/3/2014.
 */
public class FileManager extends Utility {

	private Plugin plugin = null;

	private File configFile, dataFile, playerFile, dataFolder;

	private FileConfiguration config, data, players;

	public void setup(Plugin plugin) {
		this.plugin = plugin;

		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		dataFolder = new File(plugin.getDataFolder() + "/data");

		if (!dataFolder.isDirectory())
			dataFolder.mkdir();

		configFile = new File(plugin.getDataFolder(), "config.yml");

		config = plugin.getConfig();
		config.options().copyDefaults(true);

		if (!configFile.exists()) {
			saveConfig();
		}
	}

	public void createDataFile() {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		dataFile = new File(dataFolder, "data.yml");

		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException exception) {
				logger.severe("Failed to create file data.yml!");
			}
		}

		data = YamlConfiguration.loadConfiguration(dataFile);
	}

	public void createPlayerConfig() {
		if (!plugin.getDataFolder().exists())
			plugin.getDataFolder().mkdir();

		playerFile = new File(dataFolder, "players.yml");

		if (!playerFile.exists()) {
			try {
				playerFile.createNewFile();
			} catch (IOException exception) {
				logger.severe("Failed to create file players.yml");
			}
		}

		players = YamlConfiguration.loadConfiguration(playerFile);
	}

	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}

	public void saveConfig() {
		try {
			config.save(configFile);
		} catch (IOException exception) {
			logger.warn("Failed to save config.yml!");
		}
	}

	public void saveData() {
		try {
			data.save(dataFile);
		} catch (IOException exception) {
			logger.warn("Failed to save data.yml!");
		}
	}

	public void savePlayers() {
		try {
			players.save(playerFile);
		} catch (IOException exception) {
			logger.warn("Failed to save players.yml!");
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}

	public FileConfiguration getData() {
		return data;
	}

	public FileConfiguration getPlayerConfig() {
		return players;
	}
}