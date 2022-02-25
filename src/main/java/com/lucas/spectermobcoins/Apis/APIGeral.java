package com.lucas.spectermobcoins.Apis;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.lucas.spectermobcoins.Main;
import be.maximvdw.placeholderapi.PlaceholderAPI;
import be.maximvdw.placeholderapi.PlaceholderReplaceEvent;
import be.maximvdw.placeholderapi.PlaceholderReplacer;

public class APIGeral implements Listener {

	public static void CreateAccount(Player p) {
		if (!DataBase.fc.contains(String.valueOf(p.getUniqueId()))) {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", 0.0);
			DataBase.SaveConfig();
		}
	}

	public static Double getMobCoins(Player p) {
		return DataBase.fc.getDouble(String.valueOf(p.getUniqueId()) + ".MobCoins");
	}

	public static Double getMobCoins(OfflinePlayer p) {
		return DataBase.fc.getDouble(String.valueOf(p.getUniqueId()) + ".MobCoins");
	}
	
	@SuppressWarnings("deprecation")
	public static Double getMobCoins(String p) {
		return DataBase.fc.getDouble(Bukkit.getOfflinePlayer(p).getUniqueId() + ".MobCoins");
	}

	public static void addMobCoins(Player p, Double quantidade) {
		DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", getMobCoins(p) + quantidade);
		DataBase.SaveConfig();
	}

	public static void removeMobCoins(Player p, Double quantidade) {
		if (getMobCoins(p) - quantidade <= 0) {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", 0.0);
			DataBase.SaveConfig();
		} else {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", getMobCoins(p) - quantidade);
			DataBase.SaveConfig();
		}
	}

	public static void setMobCoins(Player p, Double quantidade) {
		if (quantidade < 1) {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", 0.0);
			DataBase.SaveConfig();
		} else {
			DataBase.fc.set(String.valueOf(p.getUniqueId()) + ".MobCoins", quantidade);
			DataBase.SaveConfig();
		}
	}

	public static boolean isInt(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static boolean register() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.m, "specter_mobcoins", (PlaceholderReplacer) new PlaceholderReplacer() {
			public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
				final Player player = e.getPlayer();
				if (player == null) {
					return "";
				}
				Main.getInstance();
				return getMobCoins(player).toString();
			}
		});
		return true;
	}
	
	public static boolean register2() {
		if (Bukkit.getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		PlaceholderAPI.registerPlaceholder(Main.m, "specter_mobcoins_formatted", (PlaceholderReplacer) new PlaceholderReplacer() {
			public String onPlaceholderReplace(final PlaceholderReplaceEvent e) {
				final Player player = e.getPlayer();
				if (player == null) {
					return "";
				}
				Main.getInstance();
				return FormatAPI.format(getMobCoins(player));
			}
		});
		return true;
	}
}
