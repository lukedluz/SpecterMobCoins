package com.lucas.spectermobcoins;

import java.text.NumberFormat;
import java.util.Locale;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.mysql.jdbc.Connection;

import com.lucas.spectermobcoins.Apis.APIGeral;
import com.lucas.spectermobcoins.Apis.DataBase;
import com.lucas.spectermobcoins.Apis.FormatAPI;
import com.lucas.spectermobcoins.Apis.KillEvent;
import com.lucas.spectermobcoins.Comandos.Comando;
import com.lucas.spectermobcoins.Comandos.Shop;

public class Main extends JavaPlugin {

	public static Main m;
	public static Main instance;
	public static Connection conn = null;

	public void onEnable() {

		saveDefaultConfig();

		m = this;
		Main.instance = this;

		DataBase.create();
		DataBase.SaveConfig();
		
		APIGeral.register();
		APIGeral.register2();

		Bukkit.getPluginManager().registerEvents(new FormatAPI(), this);
		Bukkit.getPluginManager().registerEvents(new Comando(), this);
		Bukkit.getPluginManager().registerEvents(new Shop(), this);
		Bukkit.getPluginManager().registerEvents(new APIGeral(), this);
		Bukkit.getPluginManager().registerEvents(new KillEvent(), this);
		getCommand("mobcoins").setExecutor(new Comando());
		getCommand("lojamobcoins").setExecutor(new Shop());
	};

	public static Main getInstance() {
		return Main.instance;
	}

	public static String format(final double valor) {
		final NumberFormat format = NumberFormat.getInstance(Locale.GERMAN);
		format.setMaximumFractionDigits(1);
		return format.format(valor);
	}

}