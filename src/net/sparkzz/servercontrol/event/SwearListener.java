package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.util.FileManager;
import net.sparkzz.servercontrol.util.MsgHandler;
import net.sparkzz.servercontrol.util.Options;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Brendon on 7/3/2014.
 */
public class SwearListener implements Listener {

	public SwearListener() {
		createSwearList();
	}

	private static HashMap<String, String> swearlist = new HashMap<String, String>();
	private static SwearListener instance = new SwearListener();
	private FileManager files = FileManager.getManager();
	private Options option;
	private MsgHandler msg = MsgHandler.getHandler();

	public static SwearListener getInstance() {
		return instance;
	}

	public boolean removeSwear(String swear) {
		if (swearlist.containsKey(swear)) {
			swearlist.remove(swear);
			return true;
		} else return false;
	}

	public void addSwear(String swear, String replacement) {
		swearlist.put(swear, replacement);
	}

	public void clearSwears() {
		swearlist.clear();
	}

	public void createSwearList() {
		List<String> list = files.getConfig().getStringList("swears");

		for (String word : list) {
			String[] args = word.split(":");

			swearlist.put(args[0], args[1]);
		}
	}

	public void reloadSwears() {
		swearlist.clear();

		createSwearList();
	}

	//TODO: setup strict mode | special characters return null

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSwear(AsyncPlayerChatEvent event) {
		final int mode = option.getValue(Options.SWEAR_PROTECT_MODE);
		final boolean strict = option.getOption(Options.SWEAR_PROTECT_STRICT);

		boolean lowercase = option.getOption(Options.LOWERCASE_CHAT), swore = false,
				warn = option.getOption(Options.SWEAR_WARN);

		String message = event.getMessage(), args[] = message.split(" "), clean = "",
				warning = option.getMessage(Options.SWEAR_WARNING);
		/*
		 * mode default replaces with asterisks
		 * mode 1       replace with new word/string
		 * mode 2       cancel chat event
		 */
		switch (mode) {
			case 1:
				for (int i = 0; i < args.length; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");

					if (swearlist.containsKey(noSpec.toLowerCase())) {
						args[i] = swearlist.get(args[i].toLowerCase());
						swore = true;
					}

					clean += args[i] + " ";
				}

				if (lowercase)
					clean.toLowerCase();

				event.setMessage(clean);

				if (swore && warn && !warning.equals(""))
					msg.send(event.getPlayer(), warning);

				break;
			case 2:
				for (int i = 0; i < args.length; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");

					if (swearlist.containsKey(noSpec.toLowerCase()))
						swore = true;
				}

				if (swore) {
					if (warn && !warning.equals(""))
						msg.send(event.getPlayer(), warning);

					event.setCancelled(true);
				}

				break;
			default:
				for (int i = 0; i < args.length; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");

					if (swearlist.containsKey(noSpec.toLowerCase())) {
						String replacement = "";

						for (int j = 0; j < noSpec.length(); j++) {
							replacement += "*";
						}
						args[i] = replacement;
						swore = true;
					}
					clean += args[i] + " ";
				}

				if (lowercase) clean.toLowerCase();

				event.setMessage(clean);

				if (swore && warn && !warning.equals(""))
					msg.send(event.getPlayer(), warning);
				break;
		}
	}

	public HashMap<String, String> getSwearList() {
		return swearlist;
	}

	public List<String> getSwears() {
		List<String> swears = new ArrayList<String>();

		for (String swear : swearlist.keySet()) {
			swears.add(swear);
		}

		return swears;
	}
}