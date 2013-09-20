/*
 *   FoodLevelChangeListener.java - Guts of CookieCounter plugin
 *
 *   Copyright (C) 2012-2013 DMBuce <dmbuce@gmail.com>
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation; either version 2 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License along
 *   with this program; if not, write to the Free Software Foundation, Inc.,
 *   51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package com.asdflolinternet.cookiecounter;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import java.util.logging.Logger;

// config stuff
import org.bukkit.configuration.file.FileConfiguration;

public class FoodLevelChangeListener implements Listener {

	private FileConfiguration config = null;
	private Logger logger = null;

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

		String message = getNumberString() + " " + getMessageString();

		Player player = (Player) entity;
		player.sendMessage(message);
	}

	private String getNumberString() {
		int cookiesEaten = this.config.getInt("cookies_eaten");
		String numberColor = this.config.getString("number_color");
		String numberString;

		try {
			numberString = "" + ChatColor.valueOf(numberColor) + cookiesEaten + ChatColor.RESET;
		} catch (java.lang.IllegalArgumentException e) {
			getLogger().warning("Invalid config option for number_color: " + numberColor);
			numberString = "" + ChatColor.BLUE + cookiesEaten + ChatColor.RESET;
		}

		return numberString;
	}

	private String getMessageString() {
		int cookiesEaten = this.config.getInt("cookies_eaten");
		String messageColor = this.config.getString("message_color");
		String messageString;

		try {
			messageString = "" + ChatColor.valueOf(messageColor) + "cookies have been eaten." + ChatColor.RESET;
		} catch (java.lang.IllegalArgumentException e) {
			getLogger().warning("Invalid config option for message_color: " + messageColor);
			messageString = "" + ChatColor.WHITE + "cookies have been eaten." + ChatColor.RESET;
		}

		return messageString;
	}

	public void setLogger(Logger l) {
		this.logger = l;
	}

	public Logger getLogger() {
		return this.logger;
	}

}

