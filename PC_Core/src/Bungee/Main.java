package Bungee;

import java.io.IOException;

import Bungee.Commands.Restart;
import Bungee.Commands.ServerConnect;
import Bungee.Commands.ServerManager;
import Bungee.Events.PreEvents;
import Bungee.PermissionSystem.PermissionCommands;
import Bungee.Utils.Config;
import Java.MySQL;
import Java.Profile;
import Java.Server;
import Java.Servers;
import Java.Network.ProxyClient;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

public class Main extends Plugin{
	
	public static Configuration config;
	public static Plugin pl;
	public static MySQL mysql;
	
	@Override
	public void onEnable() {
		Main.pl = this;
		Config config = new Config(this);
		config.saveDefaultConfig();
		Main.config = config.getConfig();
		MySQL mysql = new MySQL("localhost", "3306", "root", "");
		Main.mysql = mysql;
		Profile.load();
		registerCommands();
		new Thread(new ProxyClient(), "ProxyReceiver").start();
	
	}
	
	private void registerCommands() {
		getProxy().getPluginManager().registerCommand(this, new Restart());
		getProxy().getPluginManager().registerCommand(this, new ServerConnect());
		getProxy().getPluginManager().registerCommand(this, new PermissionCommands());
		getProxy().getPluginManager().registerListener(this, new PreEvents());
		getProxy().getPluginManager().registerCommand(this, new ServerManager());
	}

	@Override
	public void onDisable() {
		Profile.save();
		try {
			System.out.println("[Proxy] Fechando servidor!");
			ProxyClient.server.close();
			
			System.out.println("[Proxy] Fechando conexões com cliente!");
			for(Server s : Servers.getServers()){
				s.getSocket().close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
