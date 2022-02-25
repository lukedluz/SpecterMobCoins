package com.lucas.spectermobcoins.Comandos;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.lucas.spectermobcoins.Main;
import com.lucas.spectermobcoins.Apis.APIGeral;
import com.lucas.spectermobcoins.Apis.FormatAPI;

public class Shop implements CommandExecutor, Listener {
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player s = (Player) sender;
		if (label.equalsIgnoreCase("lojamobcoins")) {
			if (args.length != 0) {
				sender.sendMessage("�cUtilize: /mobcoin - para abrir o menu de mobcoins");
				return true;
			}

			int size = Integer.valueOf(Main.m.getConfig().getString("Menu.Slots"));
			String invname = Main.m.getConfig().getString("Menu.Nome").replace("&", "�");

			final Inventory mobgui = Bukkit.createInventory(null, size, invname);

			for (String key : Main.m.getConfig().getConfigurationSection("Itens").getKeys(false)) {

				final String p = key;
				final ItemStack head = new ItemStack(
						Integer.valueOf(Main.m.getConfig().getString("Itens." + key + ".ItemId")), 1,
						(short) Double.parseDouble(Main.m.getConfig().getString("Itens." + key + ".ItemData")));
				final ItemMeta sm = head.getItemMeta();
				sm.setDisplayName(p.replace("$", "�"));

				List<String> list2 = Main.m.getConfig().getStringList("Itens." + p + ".ItemLore");
				List<String> lores = new ArrayList<String>();
				for (String string : list2) {
					lores.add(string.replace("&", "�")
							.replace("%Preco", Main.m.getConfig().getString("Itens." + p + ".Preco"))
							.replace("%MobCoinsF", FormatAPI.format(APIGeral.getMobCoins(p))).replace("%MobCoins", String.valueOf(APIGeral.getMobCoins(s))));
					sm.setLore(lores);
				}
				head.setItemMeta(sm);

				mobgui.setItem(Integer.valueOf(Main.m.getConfig().getString("Itens." + key + ".ItemSlot")) - 1, head);
			}

			s.openInventory(mobgui);
		}
		return false;
	}

	@EventHandler
	public void Execute2(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if ((e.getCurrentItem() != null) && (e.getCurrentItem().getItemMeta() != null)) {
			Inventory Inv = e.getInventory();
			if (Inv.getTitle().equalsIgnoreCase(Main.m.getConfig().getString("Menu.Nome").replace("&", "�"))) {
				e.setCancelled(true);

				String name = e.getCurrentItem().getItemMeta().getDisplayName().replace("�", "$");
				if (Main.m.getConfig().getBoolean("Itens." + name + ".Comercializavel") == true) {
					double price = Double.valueOf(Main.m.getConfig().getString("Itens." + name + ".Preco"));

					if (APIGeral.getMobCoins(p) >= price) {
						APIGeral.removeMobCoins(p, price);

						for (String Status : Main.m.getConfig().getStringList("Itens." + name + ".Comandos")) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), Status.replace("%p", p.getName()));
						}
						p.sendMessage("�aItem comprado com sucesso");

						p.closeInventory();
						p.chat("/lojamobcoins");

					} else {
						p.sendMessage("�cVoc� n�o tem mobcoins para comprar isso");
					}
				}
			}
		}
	}
}