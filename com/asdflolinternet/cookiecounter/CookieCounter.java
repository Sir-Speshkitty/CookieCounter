package com.asdflolinternet.cookiecounter;

//import com.asdflolinternet.cookiecounter.FoodLevelListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class CookieCounter extends JavaPlugin {

	private FoodLevelChangeListener cookieListener = null;

 	@Override
	public void onEnable() {
		// save default config.yml if not present
		this.saveDefaultConfig();

		// create listener for cookies eaten
		this.cookieListener = new FoodLevelChangeListener(this.getConfig());

		// add listener for when players eat
		getServer().getPluginManager().registerEvents(cookieListener, this);
	}

 	@Override
	public void onDisable() {
		this.saveConfig();
	}

}

