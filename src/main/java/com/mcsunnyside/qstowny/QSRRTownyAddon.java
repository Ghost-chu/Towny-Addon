package com.mcsunnyside.qstowny;


import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.Shop.ShopCreateEvent;
import org.maxgamer.quickshop.Shop.ShopPreCreateEvent;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.TownBlock;
import com.palmergames.bukkit.towny.object.TownBlockType;
import com.palmergames.bukkit.towny.object.TownyUniverse;

public class QSRRTownyAddon extends JavaPlugin implements Listener {
	boolean fail2load = false;
	QuickShop qs = null;
	Towny towny = null;
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
		qs = (QuickShop) Bukkit.getPluginManager().getPlugin("QuickShop");
		towny = (Towny) Bukkit.getPluginManager().getPlugin("Towny");
		//Verify plugin is reremake or reremake's forks
		try {
			QuickShop.getVersion();
		}catch (Exception e) {
			getLogger().severe("QSRR Towny Addon only can support under QuickShop-Reremake by Ghost_chu, we can't promise it can working perfactly under other forks");
			fail2load=true;
			Bukkit.getPluginManager().disablePlugin(this);
		}finally {
			if(fail2load)
				return;
		}
		if(fail2load)
			return;
		saveDefaultConfig();
		getLogger().info("Successfully loaded QSRR's towny addon.");
		
	}
	@Override
	public void onDisable() {
		if(fail2load)
			return;
	}
	@EventHandler
	public void shopCreateEvent (ShopPreCreateEvent e) {
		if(e.getPlayer().hasPermission("quickshop.addon.towny.bypass")) {
			return;
		}
		TownBlock townBlock = null;
		townBlock = TownyUniverse.getTownBlock(e.getLocation());
		if(townBlock==null) {
			e.setCancelled(true);
			return;
		}
		TownBlockType tBlockType = townBlock.getType();
		if(tBlockType!=TownBlockType.COMMERCIAL) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(getConfig().getString("messages.not-shop-plot"));
		}
	}
	@EventHandler
	public void shopCreateEvent (ShopCreateEvent e) {
		if(e.getPlayer().hasPermission("quickshop.addon.towny.bypass")) {
			return;
		}
		TownBlock townBlock = null;
		townBlock = TownyUniverse.getTownBlock(e.getShop().getLocation());
		if(townBlock==null) {
			e.setCancelled(true);
			return;
		}
		TownBlockType tBlockType = townBlock.getType();
		if(tBlockType!=TownBlockType.COMMERCIAL) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(getConfig().getString("messages.not-shop-plot"));
		}
	}
}
