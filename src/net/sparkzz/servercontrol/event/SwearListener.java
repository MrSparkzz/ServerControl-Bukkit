package net.sparkzz.servercontrol.event;

import java.util.HashMap;
import java.util.List;

import net.sparkzz.servercontrol.Main;
import net.sparkzz.util.FileManager;
import net.sparkzz.util.MsgHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SwearListener implements Listener {

	static FileManager file = Main.getManager();
	static MsgHandler msg = MsgHandler.getInstance();
	
	private static HashMap<String, String> swearlist;
	{
		swearlist = new HashMap<String, String>();
	}
	
	public void createSwearList() {
		List<String> list = file.getConfig().getStringList("swears");
		
		int i = 0, j = 1;
		
		for (String str : list) {
			String[] args = str.split(":");
			
			swearlist.put(args[i], args[j]);
			
			//i += 2;
			//j += 2;
		}
	}
	
	public void addSwear(String key, String value) {
		swearlist.put(key, value);
	}
	
	public void clearSwears() {
		swearlist.clear();
	}
	
	public HashMap<String, String> getSwears() {
		return swearlist;
	}
	
	public void removeSwear(String key) {
		swearlist.remove(key);
	}
	
	public void reloadSwears() {
		swearlist.clear();
		
		createSwearList();
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onSwear(AsyncPlayerChatEvent event) {
		final int mode = file.getConfig().getInt("messaging.clean.mode");
		
		String message = event.getMessage();
		
		String args[] = message.split(" ");
		
		String newMessage = "";
		String warn = file.getConfig().getString("messaging.clean.warn");
		
		boolean containsSwear = false;
		boolean toLower = file.getConfig().getBoolean("messaging.lower");
		
		/*
		 * mode default = replace with stars
		 * mode 1       = replace with string
		 * mode 2       = cancel chat event, warn player
		 */
		
		switch (mode) {
			case 1:
				for (int i = 0 ; i < args.length ; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");
					
					if (swearlist.containsKey(noSpec.toLowerCase())) {
						args[i] = swearlist.get(args[i].toLowerCase());
						containsSwear = true;
					}
					
					newMessage += args[i] + " ";
				}
				
				if (toLower)
					newMessage.toLowerCase();
				
				event.setMessage(newMessage);
				
				if (containsSwear)
					if (!warn.equalsIgnoreCase(""))
						msg.send(event.getPlayer(), warn);
				break;
			case 2:
				for (int i = 0 ; i < args.length ; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");
					
					if (swearlist.containsKey(noSpec.toLowerCase()))
						containsSwear = true;
						continue;
				}
				
				if (containsSwear) {
					if (!warn.equalsIgnoreCase(""))
						msg.send(event.getPlayer(), warn);
					event.setCancelled(true);
				}
				break;
			default:
				for (int i = 0 ; i < args.length ; i++) {
					String noSpec = args[i].replaceAll("[^a-zA-Z0-9]", "");
					
					if (swearlist.containsKey(noSpec.toLowerCase())) {
						String blank = "", ast = "*";
						
						for (int j = 0 ; j < noSpec.length() ; j++) {
							blank += ast;
						}
						
						args[i] = blank;
						containsSwear = true;
					}
					newMessage += args[i] + " ";
				}
				
				if (toLower)
					newMessage.toLowerCase();
				
				event.setMessage(newMessage);
				
				if (containsSwear)
					if (!warn.equalsIgnoreCase(""))
						msg.send(event.getPlayer(), warn);
				break;
		}
	}

	static SwearListener instance = new SwearListener();
	
	public static SwearListener getInstance() {
		return instance;
	}
}