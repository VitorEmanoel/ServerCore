package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

public class Food implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("food")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.HELPER, Main.servername)){
				if(args.length == 0){
					if(sender instanceof Player){
						Player p = (Player)sender;
						if(p.getFoodLevel() >= 20){
							p.sendMessage("§9Fome> §cVocê esta sem fome!");
							return true;
						}
						p.setFoodLevel(20);
						p.sendMessage("§9Fome> §fSua fome foi saciada!");
					}else{
						sender.sendMessage("§9Fome> §eUse §f/fome (Player)");
					}
				}
				if(args.length == 1){
					Player p2 = Bukkit.getPlayer(args[0]);
					if(p2 == null){
						sender.sendMessage("§9Fome> §cEste player não esta online!");
						return true;
					}
					if(p2.getFoodLevel() >= 20){
						sender.sendMessage("§9Fome> §e" + p2.getDisplayName() + " §festa sem fome!");
						return true;
					}
					p2.setFoodLevel(20);
					sender.sendMessage("§9Fome> §fA fome de §e" + p2.getDisplayName() + " §ffoi saciada!");
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
			
		}
		return false;
	}

}
