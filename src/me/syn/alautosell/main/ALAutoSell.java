package me.syn.alautosell.main;

import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.syn.alenchant.main.ALEnchantAPI;
import me.syn.algangs.utils.Message;

public class ALAutoSell {

	@SuppressWarnings("deprecation")
	public static void blockBreak(Player p, Block b) {
		if (!WorldGuardPlugin.inst().canBuild(p, b))
			return;
		if (p.getGameMode() != GameMode.SURVIVAL)
			return;
		Random r = new Random();
		if (b.getType() == Material.COBBLESTONE || b.getType() == Material.STONE || b.getType() == Material.STAINED_CLAY
				|| b.getType() == Material.NETHERRACK || b.getType() == Material.ENDER_STONE
				|| b.getType() == Material.OBSIDIAN || b.getType() == Material.QUARTZ
				|| b.getType() == Material.QUARTZ_BLOCK || b.getType() == Material.PRISMARINE) {
			ItemStack i = (ItemStack) b.getDrops().toArray()[0];
			if (p.getInventory().getItemInHand() != null) {
				i.setAmount(i.getAmount() + r.nextInt(
						p.getInventory().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS)
								+ 1));
			}
			if (ALEnchantAPI.enchantLevel(p.getInventory().getItemInHand(), "AutoSmelt") > 0) {
				if (i.getType() == Material.COBBLESTONE) {
					i.setType(Material.STONE);
				}
				// ToDo: More Autosmelt options.
			}
			if (ALEnchantAPI.enchantLevel(p.getInventory().getItemInHand(), "AutoSell") > 0) {
				for (String str : Main.order) {
					if (p.hasPermission("alautosell.shop." + str)) {
						for (String str2 : Main.shops.get(str)) {
							String[] s = str2.split(" ");
							if (b.getType().toString().toString().equalsIgnoreCase(s[0].split(";")[0])) {
								int da = Integer.valueOf(s[0].split(";")[1]);
								if (b.getData() == da) {
									double d = Double.valueOf(s[1]);
									d = d * i.getAmount();
									Main.eco.depositPlayer((OfflinePlayer) p, d * Multi.total(p));
									Tracker.blocks.put(p, Tracker.blocks.get(p) + 1);
									Tracker.items.put(p, Tracker.items.get(p) + i.getAmount());
									Tracker.money.put(p, Tracker.money.get(p) + d * Multi.total(p));
									return;
								}
							}
						}
					}
				}
			} else {
				if (p.getInventory().firstEmpty() == -1) {
					Message.player("&4&lWARNING&c Your inventory is full", p);
				}
				p.getInventory().addItem(i);
			}
			return;
		}
		if (p.getInventory().firstEmpty() == -1) {
			Message.player("&4&lWARNING&c Your inventory is full", p);
		}
		if (b.getDrops().toArray().length > 0) {
			ItemStack i = (ItemStack) b.getDrops().toArray()[0];
			p.getInventory().addItem(i);
		}
		return;

	}

	public static void blockBreak(Player p, Location l, Material m, int data) {
		if (p.getGameMode() != GameMode.SURVIVAL)
			return;
		Random r = new Random();
		if (m == Material.COBBLESTONE || m == Material.STONE || m == Material.STAINED_CLAY || m == Material.NETHERRACK
				|| m == Material.ENDER_STONE || m == Material.OBSIDIAN || m == Material.QUARTZ
				|| m == Material.QUARTZ_BLOCK || m == Material.PRISMARINE) {
			ItemStack i = new ItemStack(m);
			if (p.getInventory().getItemInHand() != null) {
				i.setAmount(i.getAmount() + r.nextInt(
						p.getInventory().getItemInHand().getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_BLOCKS)
								+ 1));
			}
			if (ALEnchantAPI.enchantLevel(p.getInventory().getItemInHand(), "AutoSmelt") > 0) {
				if (i.getType() == Material.COBBLESTONE) {
					i.setType(Material.STONE);
				}
				// ToDo: More Autosmelt options.
			}
			if (ALEnchantAPI.enchantLevel(p.getInventory().getItemInHand(), "AutoSell") > 0) {
				for (String str : Main.order) {
					if (p.hasPermission("alautosell.shop." + str)) {
						for (String str2 : Main.shops.get(str)) {
							String[] s = str2.split(" ");
							if (m.toString().equalsIgnoreCase(s[0].split(";")[0])) {
								int da = Integer.valueOf(s[0].split(";")[1]);
								if (data == da) {
									double d = Double.valueOf(s[1]);
									d = d * i.getAmount();
									Main.eco.depositPlayer((OfflinePlayer) p, d * Multi.total(p));
									Tracker.blocks.put(p, Tracker.blocks.get(p) + 1);
									Tracker.items.put(p, Tracker.items.get(p) + i.getAmount());
									Tracker.money.put(p, Tracker.money.get(p) + d * Multi.total(p));
									return;
								}
							}
						}
					}
				}
			} else {
				if (p.getInventory().firstEmpty() == -1) {
					Message.player("&4&lWARNING&c Your inventory is full",p);
				}
				p.getInventory().addItem(i);
			}
			return;
		}
		if (p.getInventory().firstEmpty() == -1) {
			Message.player("&4&lWARNING&c Your inventory is full",p);
		}
		p.getInventory().addItem(new ItemStack(m));
		return;

	}
}
