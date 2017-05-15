package Bukkit;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import Bukkit.Utils.BungeeChannelAPI;
import Bukkit.Utils.ProfileUtils;
import Bukkit.Main;
import Bukkit.Commands.Craft;
import Bukkit.Commands.Fly;
import Bukkit.Commands.Food;
import Bukkit.Commands.Freeze;
import Bukkit.Commands.Gamemode;
import Bukkit.Commands.Heal;
import Bukkit.Commands.InventoryClear;
import Bukkit.Commands.Ping;
import Bukkit.Commands.Speed;
import Bukkit.Commands.Spy;
import Bukkit.Commands.Teleport;
import Bukkit.Events.Events;
import Bukkit.Network.ClientProxy;
import Bukkit.Network.Network;
import Java.MySQL;
import Java.Status;

public class Main extends JavaPlugin{
	
	public static JavaPlugin pl;
	public static Configuration config;
	public static BungeeChannelAPI bungeeAPI;
	public static String servername;
	public static MySQL mysql;
	
	public static Network network ;
	public static ClientProxy client;
	
	@Override
	public void onEnable() {
		Main.pl = this;
		BungeeChannelAPI ch = new BungeeChannelAPI(this);
		Main.bungeeAPI = ch;
		saveDefaultConfig();
		config = getConfig();
		register();
		String sv = config.getString("servername");
		if(!sv.equals("Bungeecord"))Main.servername = sv;
		MySQL mysql = new MySQL("localhost", "3306", "root", "");
		Main.mysql = mysql;
		ProfileUtils.load();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				network = new Network();
				client = new ClientProxy(network, Status.LIGADO);
				new Thread(client).start();
			}
		}).start();
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll();
		ProfileUtils.save();
		if(network != null){
			client = new ClientProxy(network, Status.DESLIGADO);
			new Thread(client).start();
		}
	}
	
	private void register(){
		PluginManager pm = Bukkit.getPluginManager();
		getCommand("tp").setExecutor(new Teleport());
		getCommand("tph").setExecutor(new Teleport());
		getCommand("tpa").setExecutor(new Teleport());
		getCommand("spy").setExecutor(new Spy());
		getCommand("speed").setExecutor(new Speed());
		getCommand("ping").setExecutor(new Ping());
		getCommand("limpar").setExecutor(new InventoryClear());
		getCommand("curar").setExecutor(new Heal());
		getCommand("gamemode").setExecutor(new Gamemode());
		getCommand("congelar").setExecutor(new Freeze());
		getCommand("descongelar").setExecutor(new Freeze());
		getCommand("craft").setExecutor(new Craft());
		getCommand("fly").setExecutor(new Fly());
		getCommand("food").setExecutor(new Food());
		pm.registerEvents(new Events(), this);
		

	}

}
