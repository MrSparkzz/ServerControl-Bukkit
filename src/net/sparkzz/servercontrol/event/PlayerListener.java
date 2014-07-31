package net.sparkzz.servercontrol.event;

import net.sparkzz.servercontrol.players.User;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import sun.org.mozilla.javascript.internal.ContextFactory;

/**
 * Created by Brendon on 7/16/2014.
 */
public class PlayerListener implements Listener {

	User user;

	//TODO: More debugging in the invsee feature (works when both players are op, but wont work when one is op and one is not)

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onInventoryClick(InventoryClickEvent event) {
		if (user.isInvsee((Player) event.getWhoClicked())) {
			final Inventory top = event.getView().getTopInventory();
			final InventoryType type = top.getType();

			if (type == InventoryType.PLAYER) {
				final HumanEntity entity = event.getWhoClicked();
				final InventoryHolder invHolder = top.getHolder();
				final HumanEntity invOwner = (HumanEntity) invHolder;

				if (!entity.hasPermission("server.invsee.modify") || !(entity.hasPermission("server.invsee.bypass") && invOwner.hasPermission("server.invsee.preventmodify")))
					event.setCancelled(true);
			}
		}
	}

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onCloseInventory(InventoryCloseEvent event) {
		if (user.isInvsee((Player) event.getPlayer())) {
			user.setInvsee((Player) event.getPlayer(), false);
		}
	}
}