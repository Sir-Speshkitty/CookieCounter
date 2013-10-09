package com.asdflolinternet.cookiecounter;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;

public class PlayerItemConsume implements Listener {
	private CookieCounter mainClass;
	private ChatColor numberColour, messageColour;
	
	public PlayerItemConsume(Plugin plugin) {
		mainClass = (CookieCounter) plugin;
		messageColour = ChatColor.getByChar(mainClass.getConfig().getString("serverwide.message_colour"));
		numberColour = ChatColor.getByChar(mainClass.getConfig().getString("serverwide.number_colour"));
		if (messageColour == null || numberColour == null) {
			mainClass.getLogger().warning("Error setting up colours - are you sure they're valid?");
			messageColour = ChatColor.WHITE;
			numberColour = ChatColor.BLUE;
			
			mainClass.getConfig().set("serverwide.message_colour", messageColour.getChar());
			mainClass.getConfig().set("serverwide.number_colour", numberColour.getChar());
			mainClass.saveConfig();
		}
	}
	
	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		if (!event.getItem().getType().equals(Material.COOKIE)) {
			return;
		}
		
		setServerCookiesEaten(getServerCookiesEaten() + 1);
		setPersonalCookiesEaten(event.getPlayer(), getPersonalCookiesEaten(event.getPlayer()) + 1);
		
		event.getPlayer().sendMessage(BuildCookiesEaten());
		event.getPlayer().sendMessage(BuildCookiesEaten(event.getPlayer()));
	}
	
	private String BuildCookiesEaten() {
		return numberColour + "" + getServerCookiesEaten() + " cookie" + (getServerCookiesEaten() == 1 ? "" : "s") + " have been eaten on the server." + ChatColor.RESET;
	}
	
	private String BuildCookiesEaten(Player player) {
		return messageColour + "You have eaten " + numberColour + "" + getPersonalCookiesEaten(player) + messageColour + " cookie" + (getPersonalCookiesEaten(player) == 1 ? "" : "s") + "!";
	}
	
	private int getPersonalCookiesEaten(Player player) {
		return mainClass.getConfig().getInt("players." + player.getName() + ".cookies_eaten", 0);
	}
	
	private int getServerCookiesEaten() {
		return mainClass.getConfig().getInt("serverwide.cookies_eaten", 0);
		
	}
	
	private void setPersonalCookiesEaten(Player player, int amount) {
		mainClass.getConfig().set("players." + player.getName() + ".cookies_eaten", amount);
		mainClass.saveConfig();
	}
	
	private void setServerCookiesEaten(int amount) {
		mainClass.getConfig().set("serverwide.cookies_eaten", amount);
		mainClass.saveConfig();
	}
}
