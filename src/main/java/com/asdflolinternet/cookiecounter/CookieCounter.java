/*
 *   CookieCounter.java - Main class for CookieCounter plugin
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

//import com.asdflolinternet.cookiecounter.FoodLevelListener;

import org.bukkit.plugin.java.JavaPlugin;

public final class CookieCounter extends JavaPlugin {
	
	@Override
	public void onEnable() {
		// save default config.yml if not present
		this.saveDefaultConfig();
		
		// add listener for when players eat
		getServer().getPluginManager().registerEvents(new PlayerItemConsume(this), this);
	}
	
	@Override
	public void onDisable() {
		this.saveConfig();
	}
	
}
