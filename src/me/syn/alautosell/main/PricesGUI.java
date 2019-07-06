package me.syn.alautosell.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.syn.alenchant.utils.Format;

public class PricesGUI implements Listener {

	public static List<Player> open = new ArrayList<Player>();

	public static Inventory menu(Player p, String shop) {
		Inventory inv = Bukkit.createInventory(null, 27, Format.color("&6&l" + shop + " Prices"));
		for (String s : Main.shops.get(shop)) {
			double d = Double.valueOf(s.split(" ")[1]);
			ItemStack i = new ItemStack(Material.getMaterial(s.split(" ")[0].split(";")[0]));
			i.setDurability((byte) Byte.valueOf(s.split(" ")[0].split(";")[1]));
			ItemMeta m = i.getItemMeta();
			List<String> l = new ArrayList<String>();
			m.setDisplayName(Format.color("&6&l" + i.getType().toString().toLowerCase() + ":" + i.getDurability()));
			l.add(Format.color("&7"));
			l.add(Format.color("&7 &lNoMulti"));
			l.add(Format.color("&7 &7 &l»&7 Individual: &6$" + Format.decimals(0, d)));
			l.add(Format.color("&7 &7 &l»&7 Stack: &6$" + Format.decimals(0, d * 64)));
			l.add(Format.color("&7 &lMulti"));
			l.add(Format.color("&7 &7 &l»&7 Individual: &6$" + Format.decimals(0, d * Multi.total(p))));
			l.add(Format.color("&7 &7 &l»&7 Stack: &6$" + Format.decimals(0, (d * 64) * Multi.total(p))));
			l.add(Format.color("&7"));
			m.setLore(l);
			i.setItemMeta(m);
			inv.addItem(i);
		}
		return inv;
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (open.contains((Player) e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if (open.contains(p))
			open.remove(p);
	}
}
