package me.syn.alautosell.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.syn.alecho.main.Relics;
import me.syn.alenchant.enchants.EnchCombo;
import me.syn.alenchant.main.ALEnchantAPI;
import me.syn.algangs.main.ALGangsAPI;

public class Multi {

	public static List<String> order = new ArrayList<String>();
	public static HashMap<String, Double> playerMultis = new HashMap<String, Double>();
	public static HashMap<String, Double> permMultis = new HashMap<String, Double>();

	public static double total(Player p) {
		return 1.0 + personal(p) + permission(p) + relic(p) + greed(p) + gang(p) + combo(p);
	}

	public static double personal(Player p) {
		if (playerMultis.containsKey(p.getName())) {
			return playerMultis.get(p.getName());
		}
		return 0.0;
	}

	public static double permission(Player p) {
		for (String str : order) {
			if (p.hasPermission("alautosell.multi." + str)) {
				return permMultis.get(str);
			}
		}
		return 0.0;
	}

	public static double relic(Player p) {
		return (double) Relics.getBoost(p.getName()) / 100;
	}

	public static double greed(Player p) {
		ItemStack i = p.getInventory().getItemInHand();
		if (i == null || i.getType().equals(Material.AIR))
			return 0.0;
		int lvl = ALEnchantAPI.enchantLevel(i, "Greed");
		if (lvl > 0) {
			double d = lvl + 0.0;
			return d / 100;
		}
		return 0.0;
	}

	public static double gang(Player p) {
		if (ALGangsAPI.hasGang(p)) {
			return (ALGangsAPI.getMoneyBoost(ALGangsAPI.getGang(p)) + 0.0) / 100;
		}
		return 0.0;
	}

	public static double combo(Player p) {
		return (double) EnchCombo.getCombo(p)/100;
	}
}
