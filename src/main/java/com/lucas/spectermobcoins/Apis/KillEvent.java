package com.lucas.spectermobcoins.Apis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class KillEvent implements Listener {

	@EventHandler
	public void onKill(EntityDeathEvent e) {
		double stack = 0.0;
		if (e.getEntity() instanceof Player)
			return;

		if (!(e.getEntity().getKiller() instanceof Player))
			return;

		Player p = e.getEntity().getKiller();
		if (e.getEntity().hasMetadata("atlas_spawners_quantidade")) {
			if (e.getEntity().getMetadata("atlas_spawners_quantidade") != null) {
				if (!e.getEntity().getMetadata("atlas_spawners_quantidade").isEmpty()) {
					if (e.getEntity().getMetadata("atlas_spawners_quantidade").get(0) != null) {
						if (p.isSneaking()) {
							stack = 1;
							APIGeral.addMobCoins(p, stack);
						} else {
							stack = e.getEntity().getMetadata("atlas_spawners_quantidade").get(0).asDouble();
							APIGeral.addMobCoins(p, stack);
						}
					}
				}
			}
		}
	}
}
