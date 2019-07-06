package me.syn.alautosell.main;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.syn.alautosell.utils.Message;
import me.syn.algangs.utils.Format;

public class Tracker implements Listener {

	public static HashMap<Player, Integer> blocks = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> items = new HashMap<Player, Integer>();
	public static HashMap<Player, Double> money = new HashMap<Player, Double>();

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		blocks.put(p, 0);
		items.put(p, 0);
		money.put(p, 0.0);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		blocks.remove(p);
		items.remove(p);
		money.remove(p);
	}

	@SuppressWarnings("deprecation")
	public static void timer() {
		Bukkit.getServer().getScheduler().scheduleAsyncRepeatingTask(Main.instance, new Runnable() {
			public void run() {
				if (Bukkit.getOnlinePlayers().size() > 0) {
					for (Player p : Bukkit.getOnlinePlayers()) {
						if (money.get(p) == 0.0)
							continue;
						Message.player(Main.prefix + "AutoSell information for the last minute:", p);
						Message.player("&8 &8 ● &7Mined &7" + Format.decimals(0, blocks.get(p) + 0.0)
								+ "&7 blocks &8(&7" + Format.decimals(0, items.get(p) + 0.0) + "&7 items&8)", p);
						Message.player(
								"&8 &8 ● &7Multiplier &7" + Format.decimals(1, ((Multi.total(p) - 1) * 100)) + "%", p);
						Message.player("&8 &8 ● &7Money made &7" + Format.number(money.get(p)), p);
						blocks.put(p, 0);
						items.put(p, 0);
						money.put(p, 0.0);
					}
				}
			}
		}, 1200, 1200); // 1200
	}
}
