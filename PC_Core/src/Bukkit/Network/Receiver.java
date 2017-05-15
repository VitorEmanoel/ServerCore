package Bukkit.Network;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import Bukkit.Main;

public class Receiver implements Runnable{


	private Socket s;
	public Receiver(Socket s) {
		this.s = s;
	}
	
	public void freeze(Player p, boolean b){
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(b){
					p.setWalkSpeed(0F);
				}else{
					p.setWalkSpeed(0.2F);
				}
			}
		}.runTaskLater(Main.pl, 1);
	}
	
	@Override
	public void run() {
		try {
			Scanner scanner = new Scanner(s.getInputStream());
			while(scanner.hasNextLine()){
				String text = scanner.nextLine();
				System.out.println("[Servidor] Recebendo mensagem do server!");
				System.out.println("[Servidor] Mensagem: " );
				System.out.println("[Servidor]    " + text);
				if(text.equalsIgnoreCase("off")){
					System.out.println("[Servidor] Desligando servidor!");
					Bukkit.shutdown();
					continue;
				}else if(text.split("-")[0].equalsIgnoreCase("nomove")){
					String player = text.split("-")[1];
					Player p = Bukkit.getPlayer(player);
					if(p != null){
						freeze(p, true);
						continue;
					}
				}else if(text.split("-")[0].equalsIgnoreCase("move")){
					String player = text.split("-")[1];
					Player p = Bukkit.getPlayer(player);
					if(p != null){
						freeze(p, false);
						continue;
					}
				}
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
