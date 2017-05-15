package Bukkit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;

public class Gamemode implements CommandExecutor{
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("gamemode")){
			Profile pm = Profile.get(sender.getName());
			if(pm.hasPermission(Group.ADMINISTRATOR, Main.servername)){
				if(args.length == 0){
					sender.sendMessage("§9GameMode> §eUse §f/gamemode (0|1|2|3)");
					sender.sendMessage("§9GameMode> §fAliase: /gm");
					return true;
				}
				if(args.length == 1){
					if(sender instanceof Player){
						Player p = (Player)sender;
						try{
							int gm2 = Integer.parseInt(args[0]);
							setGameMode(p, gm2);
						}catch(NumberFormatException e){
							String gm2 = args[0];
							setGameMode(p, gm2);
							return true;
						}
					}else{
						sender.sendMessage("§9GameMode> §eUse §f/gamemode (0|1|2|3)");
						sender.sendMessage("§9GameMode> §fAliase: /gm");
					}
				}
				if(args.length == 2){
					Player p2 = Bukkit.getPlayer(args[1]);
					if(p2 == null){
						sender.sendMessage("§9GameMode> §cEste player não esta online!");
						return true;
					}
					try{
						int gm2 = Integer.parseInt(args[0]);
						setGameMode(sender, p2, gm2);
					}catch(NumberFormatException e){
						String gm2 = args[0];
						setGameMode(sender, p2, gm2);
					}
				}
			}else{
				sender.sendMessage("§cVocê não tem permissão para fazer isto!");
			}
		}
		return false;
	}
	
	private void setGameMode(Player p, String gm2){
		try{
			GameMode gm = GameMode.valueOf(gm2.toUpperCase());
			if(p.getGameMode().equals(gm)){
				p.sendMessage("§9GameMode> §cVocê já esta no modo de jogo §f" + gm.toString());
				return;
			}
			p.sendMessage("§9GameMode> §fSeu modo de jogo foi alterado para §f" + gm.toString());
			p.setGameMode(gm);
			return;
		}catch(IllegalArgumentException e){
			p.sendMessage("§9GameMode> §cModo de jogo invalido!");
		}

	}
	
	@SuppressWarnings("deprecation")
	private void setGameMode(Player p, int gm2){
		try{
			GameMode gm = GameMode.getByValue(gm2);
			if(p.getGameMode().equals(gm)){
				p.sendMessage("§9GameMode> §cVocê já esta no modo de jogo §f" + gm.toString());
				return;
			}
			p.sendMessage("§9GameMode> §fSeu modo de jogo foi alterado para §f" + gm.toString());
			p.setGameMode(gm);
			return;
		}catch(IllegalArgumentException | NullPointerException e){
			p.sendMessage("§9GameMode> §cModo de jogo invalido!");
		}

				
	}
	
	@SuppressWarnings("deprecation")
	private void setGameMode(CommandSender p, Player p2, int gm2){
		try{
			GameMode gm = GameMode.getByValue(gm2);
			if(p2.getGameMode().equals(gm)){
				p.sendMessage("§9GameMode> §cVoc§ já esta no modo de jogo §f" + gm.toString());
				return;
			}
			p.sendMessage("§9GameMode> §fModo de jogo de " + p2.getDisplayName() + "foi alterado para §f" + gm.toString());
			p2.setGameMode(gm);
			return;
		}catch(IllegalArgumentException e){
			p.sendMessage("§9GameMode> §cModo de jogo invalido!");
		}

				
	}
	
	private void setGameMode(CommandSender p, Player p2, String gm2){
		try{
			GameMode gm = GameMode.valueOf(gm2.toUpperCase());
			if(p2.getGameMode().equals(gm)){
				p.sendMessage("§9GameMode> §cVocê já esta no modo de jogo §f" + gm.toString());
				return;
			}
			p.sendMessage("§9GameMode> §fModo de jogo de " + p2.getDisplayName() + " foi alterado para §f" + gm.toString());
			p2.setGameMode(gm);
			return;
		}catch(IllegalArgumentException e){
			p.sendMessage("§9GameMode> §cModo de jogo invalido!");
		}
	}


}
