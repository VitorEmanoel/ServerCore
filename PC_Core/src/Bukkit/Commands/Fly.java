package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

public class Fly implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fly")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.HELPER, Main.servername)){
				if(args.length == 0){
					if(sender instanceof Player){
						Player p = (Player)sender;
						if(p.getAllowFlight()){
							p.setAllowFlight(false);
							p.sendMessage("§9Voar> §fModo de voo desativado.");
						}else{
							p.setAllowFlight(true);
							p.sendMessage("§9Voar> §fModo de voo ativado.");
						}
						
					}
					if(args.length == 1){
						Player p2 = Bukkit.getPlayer(args[0]);
						if(p2 == null){
							sender.sendMessage("§9Voar> §cEste player não esta online!");
							return true;
						}
						if(p2.getAllowFlight()){
							p2.setAllowFlight(false);
							sender.sendMessage("§9Voar> §fModo de voo de §e" + p2.getDisplayName() + " §fdesativado.");
							
						}else{
							p2.setAllowFlight(true);
							sender.sendMessage("§9Voar: §fModo de voo de §e" + p2.getDisplayName() + " §fativado.");
						}
					}
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}
		return false;
	}

}
