package me.syn.alautosell.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.syn.alautosell.commands.ALAutoSellCMD;
import me.syn.alautosell.events.BlockBreak;
import me.syn.alautosell.utils.Files;
import me.syn.alautosell.utils.Message;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static Main instance;
	public static String prefix = "&8&l(&6&l!&8&l)&6 &l»&7 ";
	public static Economy eco = null;

	public static List<String> order = new ArrayList<String>();
	public static HashMap<String, List<String>> shops = new HashMap<String, List<String>>();

	public void onEnable() {
		Message.console("&7");

		instance = this;
		Files.base();
		getServer().getPluginManager().registerEvents(new BlockBreak(), this);
		getServer().getPluginManager().registerEvents(new Tracker(), this);
		getServer().getPluginManager().registerEvents(new PricesGUI(), this);
		getCommand("alautosell").setExecutor(new ALAutoSellCMD());
		setupEconomy();
		if (Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new Placeholders().register();
		}
		Tracker.timer();

		Message.console("&7");
	}

	public boolean setupEconomy() {
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if (rsp == null) {
			return false;
		}
		eco = rsp.getProvider();
		return eco != null;
	}
}
