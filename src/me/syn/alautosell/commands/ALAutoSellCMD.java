package me.syn.alautosell.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.syn.alautosell.main.Main;
import me.syn.alautosell.main.Multi;
import me.syn.alautosell.main.PricesGUI;
import me.syn.alautosell.utils.Files;
import me.syn.alautosell.utils.Format;
import me.syn.alcore.Core;
import me.syn.alenchant.utils.Message;

public class ALAutoSellCMD implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lab, String[] args) {
		if (args.length == 0) {
			Message.sender(Main.prefix + "Commands for &6AfterLife-AutoSell", sender);
			Message.sender("&8 ● &6/ALAutoSell&7 View the help list", sender);
			Message.sender("&8 ● &6/ALAutoSell Prices&7 View the prices of a certain shop", sender);
			Message.sender("&8 ● &6/ALAutoSell Multi&7 View your current sell multiplier", sender);
			Message.sender("&8 ● &6/ALAutoSell MultiTop&7 View the highest multipliers", sender);
			Message.sender("&8 ● &6/ALAutoSell Sell&7 Sell the items in your inventory", sender);
			Message.sender("&8 ● &6/ALAutoSell Reload&7 Reload the config file", sender);
		} else {
			if (args[0].equalsIgnoreCase("prices")) { // AS Prices <Shop>
				if (!(sender instanceof Player))
					return true;
				if (args.length >= 2) {
					if (!Main.order.contains(args[1])) {
						Message.sender(Main.prefix + "Sorry, but &c" + args[1] + "&7 is an invalid shop", sender);
						return true;
					}
					Player p = (Player) sender;
					PricesGUI.open.add(p);
					p.openInventory(PricesGUI.menu(p, args[1]));
				} else {
					Message.sender(Main.prefix + "Usage&6 /ALAutoSell Prices <Shop>", sender);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("multi") || args[0].equalsIgnoreCase("multiplier")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					Message.player(Main.prefix + "Your current sell multiplier information:", p);
					Message.player("&8 ● &7Total: &6&l" + Format.decimals(1, ((Multi.total(p) - 1) * 100)) + "%", p);
					Message.player("&7", p);
					Message.player("&8 ● &7Personal: &6" + Format.decimals(1, (Multi.personal(p) * 100)) + "%", p);
					Message.player("&8 ● &7Permission: &6" + Format.decimals(1, (Multi.permission(p) * 100)) + "%", p);
					Message.player("&8 ● &7Relics: &6" + Format.decimals(1, (Multi.relic(p) * 100)) + "%", p);
					Message.player("&8 ● &7Greed: &6" + Format.decimals(1, (Multi.greed(p) * 100)) + "%", p);
					Message.player("&8 ● &7Gang: &6" + Format.decimals(1, (Multi.gang(p) * 100)) + "%", p);
					Message.player("&8 ● &7Combo: &6" + Format.decimals(1, (Multi.combo(p) * 100)) + "%", p);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("multitop")) {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					HashMap<String, Double> m = new HashMap<String, Double>();
					for (Player p : Bukkit.getOnlinePlayers()) {
						m.put(p.getName(), Multi.total(p) * 100);
					}
					int x = 1;
					if (args.length >= 2) {
						try {
							x = Integer.parseInt(args[1]);
						} catch (Exception ex) {
							Message.sender(Core.prefix("a") + "&7Sorry, but &C" + args[1] + "&7 is an invalid integer",
									sender);
							return true;
						}
						if (x < 1)
							x = 1;
					}
					Core.leaderboardDouble("Sell Multipliers", "6", m, 1, x, 15, true, sender);
				}
				return true;
			}
			if (args[0].equalsIgnoreCase("sell") || args[0].equalsIgnoreCase("all")) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					double d = 0.0;
					int x = 0;
					for (String str : Main.order) {
						if (p.hasPermission("alautosell.shop." + str)) {
							for (String str2 : Main.shops.get(str)) {
								for (ItemStack i : p.getInventory().getContents()) {
									if (i == null)
										continue;
									String[] s = str2.split(" ");
									if (i.getType().toString().equalsIgnoreCase(s[0].split(";")[0])) {
										int da = Integer.valueOf(s[0].split(";")[1]);
										if (i.getDurability() == da) {
											x += i.getAmount();
											d += Double.valueOf(s[1]) * i.getAmount();
											p.getInventory().removeItem(i);
											continue;
										}
									}
								}
							}
							d = d * Multi.total(p);
							p.updateInventory();
							if (x == 0) {
								Message.player(Main.prefix + "You don't have any items to sell", p);
							} else {
								Message.player(Main.prefix + "You sold &6" + Format.decimals(0, x + 0.0)
										+ "&7 items for &6$" + Format.number(d), p);
								Main.eco.depositPlayer((OfflinePlayer) p, d);
							}
							return true;
						}
					}
				}
			}
			if (args[0].equalsIgnoreCase("reload")) { // ALAutoSell Reload
				if (sender instanceof ConsoleCommandSender || sender.hasPermission("alautosell.command.reload")) {
					Main.order.clear();
					Main.shops.clear();
					Files.base();
					Message.sender(Main.prefix + "Successfully reloaded the config file", sender);
					return true;
				} else {
					Message.noPermission(sender);
				}
			}
			Message.sender(Main.prefix + "Commands for &6AfterLife-AutoSell", sender);
			Message.sender("&8 ● &6/ALAutoSell&7 View the help list", sender);
			Message.sender("&8 ● &6/ALAutoSell Prices&7 View the prices of a certain shop", sender);
			Message.sender("&8 ● &6/ALAutoSell Multi&7 View your current sell multiplier", sender);
			Message.sender("&8 ● &6/ALAutoSell Sell&7 Sell the items in your inventory", sender);
			Message.sender("&8 ● &6/ALAutoSell Reload&7 Reload the config file", sender);
		}
		return false;
	}
}
