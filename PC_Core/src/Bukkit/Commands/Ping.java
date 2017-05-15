package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

public class Ping implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Profile pm = Profile.get(sender.getName());
		if(pm.hasPermission(Group.MEMBER, Main.servername)){
			if(args.length == 0){
				if(sender instanceof Player){
					Player p = (Player)sender;
					p.sendMessage("§9Ping> §fSeu ping atual §e" + ((CraftPlayer)p).getHandle().ping);
					return true;
				}else{
					sender.sendMessage("§9Ping> §eUse §f/ping (Player)");
				}
				if(args.length == 1){
					Player p2 = Bukkit.getPlayer(args[0]);
					if(p2 == null){
						sender.sendMessage("§9Ping> §cEste player não esta online!");
						return true;
					}
					sender.sendMessage("§9Ping> §fO ping de §e" + p2.getDisplayName() + " §fé §e" + ((CraftPlayer)p2).getHandle().ping);
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}else{
			sender.sendMessage("§cVocê não tem permissão para fazer isto!");
		}
		return false;
	}

}
