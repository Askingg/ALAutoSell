package me.syn.alautosell.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.syn.alautosell.main.Main;

public class Message {

	public static void noPermission(Player p) {
		p.sendMessage(Format.color(Main.prefix + "Sorry, but you don't have permission to do that"));
	}

	public static void noPermission(CommandSender sender) {
		sender.sendMessage(Format.color(Main.prefix + "Sorry, but you don't have permission to do that"));
	}

	public static void console(String msg) {
		Bukkit.getConsoleSender().sendMessage(Format.color(msg));
	}

	public static void broadcast(String msg) {
		Bukkit.broadcastMessage(Format.color(msg));
	}

	public static void player(String msg, Player p) {
		p.sendMessage(Format.color(msg));
	}

	public static void sender(String msg, CommandSender sender) {
		sender.sendMessage(Format.color(msg));
	}
}
