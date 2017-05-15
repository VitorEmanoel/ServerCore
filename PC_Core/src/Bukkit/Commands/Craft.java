package Bukkit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

public class Craft implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("craft")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				Profile pm = Profile.get(sender.getName());
				if(!pm.hasPermission(Group.VIP, Main.servername)){
					p.sendMessage("§9Mesa de trabalho> §cVocê não tem permissão para isto!");
					return true;
				}
				p.openWorkbench(p.getLocation(), true);
				p.sendMessage("§9Mesa de trabalho> §fMesa de trabalho foi aberta!");

			}
		}
		if(cmd.getName().equalsIgnoreCase("fornalha")){
			if(sender instanceof Player){
				//Player p = (Player)sender;
				
			}
		}
		return false;
	}

}
