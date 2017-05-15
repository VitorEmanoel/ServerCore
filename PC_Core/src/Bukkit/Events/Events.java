package Bukkit.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Events implements Listener {
	
	@EventHandler
	public void e(PlayerJoinEvent e){
		e.setJoinMessage(null);
	}
	
	@EventHandler
	public void e(PlayerQuitEvent e){
		e.setQuitMessage(null);
	}
}
