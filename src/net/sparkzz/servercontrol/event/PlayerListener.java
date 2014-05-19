package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.player.User;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class PlayerListener implements Listener {
	
	User user = User.getUser();
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {
		if (user.isInvSee()) {
			final Inventory top = event.getView().getTopInventory();
			final InventoryType type = top.getType();
			
			if (type == InventoryType.PLAYER) {
				final HumanEntity entity = event.getWhoClicked();
				final InventoryHolder invHolder = top.getHolder();
				final HumanEntity invOwner = (HumanEntity) invHolder;
				
				if (!entity.hasPermission("server.invsee.modify") || invOwner.hasPermission("server.invsee.preventmodify")) {	
					event.setCancelled(true);
				}
			}
		}
	}
	
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCloseInventory(InventoryCloseEvent event) {
		if (user.isInvSee()) {
			user.setInvSee(false);
		}
	}
}
