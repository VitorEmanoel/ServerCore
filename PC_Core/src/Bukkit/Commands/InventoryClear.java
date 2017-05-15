package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import Bukkit.Main;
import Java.Group;
import Java.Profile;


public class InventoryClear implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("limpar")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.ADMINISTRATOR, Main.servername)){
				if(args.length == 0){
					if(sender instanceof Player){
						Player p = (Player)sender;
						int total = 0;
						for(ItemStack all : p.getInventory().getContents()){
							if(all != null)
								if(all.getType() != Material.AIR)
								total = total + all.getAmount();
						}
						if(total == 0){
							p.sendMessage("§9Inventario> §cSeu inventario já esta limpo!");
							return true;
						}
						p.sendMessage("§9Inventario> §fSeu inventario foi limpo com sucesso! Foram removidos §e" + total + " §fitens.");
						p.getInventory().clear();
					}else{
						sender.sendMessage("§9Inventario> §eUse §f/limpar (Player)");
					}
				}
				if(args.length == 1){
					Player p2 = Bukkit.getPlayer(args[0]);
					if(p2 == null){
						sender.sendMessage("§9Inventario> §cEste player não está online!");
						return true;
					}
					if(p2.getInventory().getContents().length == 0){
						sender.sendMessage("§9Inventario> §cO inventario de §e" + p2.getDisplayName() + " §cestá vazio!");
						return true;
					}
					sender.sendMessage("§9Inventario> §fO inventario de §e" + p2.getDisplayName() + " foi limpo com sucesso! Foram removidos §e" + p2.getInventory().getContents().length);
					p2.getInventory().clear();
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}

		
		return false;
	}

}
