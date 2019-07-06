package me.syn.alautosell.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.syn.alautosell.main.Main;
import me.syn.alautosell.main.Multi;

public class Files {

	public static File configFile;
	public static FileConfiguration config;

	public static void base() {
		Main main = Main.getPlugin(Main.class);
		if (!main.getDataFolder().exists()) {
			main.getDataFolder().mkdirs();
			Message.console(
					Main.prefix + "&7Created the '&7" + main.getDataFolder().getName().toString() + "&7' folder");
		}
		configFile = new File(main.getDataFolder(), "config.yml");
		if (!configFile.exists()) {
			main.saveResource("config.yml", false);
			Message.console(Main.prefix + "&7Created the '&7config.yml&7' file");
		}
		config = YamlConfiguration.loadConfiguration(configFile);

		for (String str : config.getConfigurationSection("shops").getKeys(false)) {
			Main.order.add(str);
			List<String> l = new ArrayList<String>();
			for (String str2 : config.getConfigurationSection("shops." + str).getKeys(false)) {
				l.add(str2 + " " + config.getDouble("shops." + str + "." + str2));
			}
			Main.shops.put(str, l);
		}
		Multi.order.clear();
		Multi.playerMultis.clear();
		Multi.permMultis.clear();
		for (String str : config.getConfigurationSection("multis.player").getKeys(false)) {
			Multi.playerMultis.put(str, config.getDouble("multis.player." + str));
		}
		for (String str : config.getConfigurationSection("multis.permission").getKeys(false)) {
			Multi.order.add(str);
			Multi.permMultis.put(str, config.getDouble("multis.permission." + str));
		}
	}
}