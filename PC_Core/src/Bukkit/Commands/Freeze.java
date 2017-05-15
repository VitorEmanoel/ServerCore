package Bukkit.Commands;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

public class Freeze implements CommandExecutor{
	
	static HashMap<UUID, Long> freeze = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("congelar")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.HELPER, Main.servername)){
				if(args.length == 0){
					sender.sendMessage("§9Congelar> §eUse §f/congelar (Player) (Tempo)");
					return true;
				}
				if(args.length == 2){
					try{
						int tempo = Integer.parseInt(args[1]);
						Player p = Bukkit.getPlayer(args[0]);
						if(p == null){
							sender.sendMessage("§9Congelar> §cEste player não esta online!");
							return true;
						}
						if(tempo <= 0){
							sender.sendMessage("§9Congelar> §cUse tempos maiores que zero!");
							return true;
						}
						if(hasFreezed(p)){
							sender.sendMessage("§9Congelar> §cEste player ja está congelado!");
							return true;
						}
						congelar(p, tempo);
						sender.sendMessage("§9Congelar> §fVocê congelou §e" + p.getDisplayName() + " §fpor §e" + tempo + " §fminuto(s).");
					}catch(ArrayIndexOutOfBoundsException e){
						sender.sendMessage("§9Congelar> §eUse §f/congelar (Player) (Tempo)");
					}
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}
		if(cmd.getName().equalsIgnoreCase("descongelar")){
			if(sender.hasPermission("projectcraft.descongelar")){
				if(args.length == 0){
					sender.sendMessage("§9Descongelar> §eUse §f/descongelar (Player)");
					return true;
				}
				if(args.length == 1){
					Player p = Bukkit.getPlayer(args[0]);
					if(p == null){
						sender.sendMessage("§9Descongelar> §cEste player não esta online!");
						return true;
					}
					if(!hasFreezed(p)){
						sender.sendMessage("§9Descongelar> §cEste player não esta congelado!");
						return true;
					}
					
					descongelar(p);
					sender.sendMessage("§9Descongelar> §fVocê descongelou §e" + p.getDisplayName() + "§f!");
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}	

		return false;
	}
	
	private void descongelar(Player p){
		if(freeze.containsKey(p.getUniqueId())){
			freeze.remove(p.getUniqueId());
		}
	}
	
	
	public boolean hasFreezed(Player p){
		if(freeze.containsKey(p.getUniqueId())){
			if(freeze.get(p.getUniqueId()) >= System.currentTimeMillis()){
				return false;
			}
		}
		return true;
	}
	
	
	private void congelar(Player p, int minutos){
		if(!freeze.containsKey(p.getUniqueId())){
			freeze.put(p.getUniqueId(), System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(minutos));
		}
	}

}
