package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;


public class Heal implements CommandExecutor{
	
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("heal")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.MODERATOR, Main.servername)){
				if(args.length == 0){
					if(sender instanceof Player){
						Player p = (Player)sender;
						if(p.getHealth() >= p.getMaxHealth()){
							p.sendMessage("§9Curar> §cVocê já esta com a sua vida cheia!");
							return true;
						}
						p.setHealth(p.getMaxHealth());
						p.sendMessage("§9Curar> §fVocê foi curado!");
					}else{
						sender.sendMessage("§9Curar> §eUse §f/curar (Player)");
					}
				}
				if(args.length == 1){
					Player p2 = Bukkit.getPlayer(args[0]);
					if(p2 == null){
						sender.sendMessage("§9Curar> §cEste player não esta online!");
						return true;
					}
					if(p2.getHealth() >= p2.getMaxHealth()){
						sender.sendMessage("§9Curar> §e" + p2.getDisplayName() + " §fja esta com a vida cheia!");
						return true;
					}
					p2.setHealth(p2.getMaxHealth());
					sender.sendMessage("§9Curar> §fVocê curou §e" + p2.getDisplayName() + "§f!");
					
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}
		return false;
	}

}
