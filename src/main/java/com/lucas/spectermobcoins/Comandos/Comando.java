package com.lucas.spectermobcoins.Comandos;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.lucas.spectermobcoins.Main;
import com.lucas.spectermobcoins.Apis.APIGeral;
import com.lucas.spectermobcoins.Apis.FormatAPI;

public class Comando implements CommandExecutor, Listener {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player p = (Player) sender;
		if (label.equalsIgnoreCase("MobCoins")) {
			if (args.length == 0) {
				if (Main.m.getConfig().getBoolean("MobCoinsFormatado") == true) {
					p.sendMessage("�aSeus mobcoins: " + FormatAPI.format(APIGeral.getMobCoins(p)));
					return true;
				} else {
					p.sendMessage("�aSeus mobcoins: " + APIGeral.getMobCoins(p));
					return true;
				}
			}
			if (args.length == 1) {
				if (Bukkit.getPlayer(args[0]) != null) {
					Player t = Bukkit.getPlayer(args[0]);
					p.sendMessage("�aMobCoins de " + t.getName() + ": " + APIGeral.getMobCoins(t));
					return true;
				}
			}
			if (args.length != 3) {
				sender.sendMessage("�cUtilize: /mobcoins - para ver seus mobcoins");
				sender.sendMessage("�cUtilize: /mobcoins (player) - para ver os mobcoins de alguem");
				if (p.hasPermission("specter.mobcoins")) {
					sender.sendMessage("�cUtilize: /mobcoins set (player) (valor) - para setar os mobcoins de um player");
					sender.sendMessage("�cUtilize: /mobcoins add (player) (valor) - para adicionar mobcoins para um player");
					sender.sendMessage("�cUtilize: /mobcoins remove (player) (valor) - para remover mobcoins de um player");
				}
				return true;
			} else {
				if (args[0].equalsIgnoreCase("set")) {
					if (p.hasPermission("specter.mobcoins")) {
						Player t = Bukkit.getPlayer(args[1]);
						if (APIGeral.isInt(args[2])) {
							APIGeral.setMobCoins(t, Double.valueOf(args[2]));
							p.sendMessage("�amobcoins setados com sucesso");
						} else {
							p.sendMessage("�cValor inv�lido");
						}
					}
				} else if (args[0].equalsIgnoreCase("add")) {
					if (p.hasPermission("specter.mobcoins")) {
						Player t = Bukkit.getPlayer(args[1]);
						if (APIGeral.isInt(args[2])) {
							APIGeral.addMobCoins(t, Double.valueOf(args[2]));
							p.sendMessage("�amobcoins adicionados com sucesso");
						} else {
							p.sendMessage("�cValor inv�lido");
						}
					}
				} else if (args[0].equalsIgnoreCase("remove")) {
					if (p.hasPermission("specter.mobcoins")) {
						Player t = Bukkit.getPlayer(args[1]);
						if (APIGeral.isInt(args[2])) {
							APIGeral.removeMobCoins(t, Double.valueOf(args[2]));
							p.sendMessage("�amobcoins removidos com sucesso");
						} else {
							p.sendMessage("�cValor inv�lido");
						}
					}
				}
			}
		}
		return false;
	}
}
