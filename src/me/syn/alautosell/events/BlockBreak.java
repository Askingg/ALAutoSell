package me.syn.alautosell.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import me.syn.alautosell.main.ALAutoSell;

public class BlockBreak implements Listener {

	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		if (e.getPlayer().getLocation().getWorld().getName().equals("world")
				&& WorldGuardPlugin.inst().canBuild(e.getPlayer(), e.getBlock())) {
			ALAutoSell.blockBreak(e.getPlayer(), e.getBlock());
			e.getBlock().setType(Material.AIR);
		}
	}
}
