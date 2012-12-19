package com.asdflolinternet.cookiecounter;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

// config stuff
import org.bukkit.configuration.file.FileConfiguration;

public class FoodLevelChangeListener implements Listener {

	private FileConfiguration config = null;

	public FoodLevelChangeListener(FileConfiguration conf) {
		this.config = conf;
	}

	@EventHandler(priority = EventPriority.NORMAL) // NORMAL by default
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		if (this.isPlayerEating(event) &&
		    this.isPlayerHoldingACookie(event.getEntity())) {

			this.aCookieWasEaten(event.getEntity());
		}
	}

	public boolean isPlayerEating(FoodLevelChangeEvent event) {
		Player player;
		try {
			player = (Player) event.getEntity();
		} catch (ClassCastException e) {
			return false;
		}
		return event.getFoodLevel() > player.getFoodLevel();
	}

	public boolean isPlayerHoldingACookie(HumanEntity player) {
		return player.getItemInHand().getType().getId() == 357;
	}

	public void aCookieWasEaten(HumanEntity entity) {
		int cookiesEaten = this.config.getInt("cookies_eaten") + 1;
		config.set("cookies_eaten", cookiesEaten);

		Player player = (Player) entity;
		player.sendMessage(cookiesEaten + " cookies have been eaten.");
	}

}

