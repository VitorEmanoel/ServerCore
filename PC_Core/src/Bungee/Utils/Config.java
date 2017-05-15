package Bungee.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
	
	private File file;
	private Configuration config;
	
	public Plugin plugin;
	public Config(Plugin main){
		plugin = main;
		
	}
	
	public void saveDefaultConfig(){
		try
		{
			if (!plugin.getDataFolder().exists())
			{
				plugin.getLogger().info("Criando arquivos de configuração!");
		        plugin.getDataFolder().mkdir();
			}
			File file = new File(plugin.getDataFolder(), "config.yml");
			if (!file.exists())
			{
				plugin.getLogger().info("Creating default configuration file - first run?");
				Files.copy(plugin.getResourceAsStream("config.yml"), file.toPath(), new CopyOption[0]);
			}
			this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		}
		catch (Exception e)
		{
			System.out.println("--------------------------------");
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getStackTrace());
			System.out.println("--------------------------------");

		}
	}
	
	public Configuration getConfig(){
		return config;
	}
	
	
	
	public void loadConfiguration(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
		} catch (IOException e) {
			System.out.println("--------------------------------");
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getStackTrace());
			System.out.println("--------------------------------");
		}
		
	}
	
	public void saveConfig(){
		try {
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
		} catch (IOException e) {
			System.out.println("--------------------------------");
			System.out.println("Cause: " + e.getCause());
			System.out.println("From: " + e.getStackTrace());
			System.out.println("--------------------------------");
		}
	}

}
