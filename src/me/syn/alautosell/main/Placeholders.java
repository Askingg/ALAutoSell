package me.syn.alautosell.main;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.syn.alautosell.utils.Format;

public class Placeholders extends PlaceholderExpansion {

	public String getIdentifier() {
		return "alautosell";
	}

	public String getPlugin() {
		return null;
	}

	public String getAuthor() {
		return "Synysterrr";
	}

	public String getVersion() {
		return "1.0";
	}

	public String onPlaceholderRequest(Player p, String identifier) {

		// Placeholder: %alautosell_multi_decimal%
		if (identifier.equalsIgnoreCase("multi_decimal")) {
			return Format.decimals(3, Multi.total(p) - 1);
		}

		// Placeholder: %alautosell_multi_percent%
		if (identifier.equalsIgnoreCase("multi_percent")) {
			return Format.decimals(1, (Multi.total(p) - 1) * 100) + "%";
		}

		return null;
	}
}