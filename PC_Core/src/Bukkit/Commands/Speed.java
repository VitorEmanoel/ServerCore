package Bukkit.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Speed implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("speed")){
			if(!(sender instanceof Player)){
				if(args.length < 2){
					sender.sendMessage("§9Velocidade> §eUse §f/speed (Player) (Velocidade)");
					return true;
				}
			}
			if(args.length == 0){
				sender.sendMessage("§9Velocidade> §eUse §f/speed (Velocidade) ou /speed (Player) (Velocidade)");
				return true;
			}else if(args.length == 1){
				Player p = (Player)sender;
				float speed = Float.valueOf(args[0]);
				if(speed > 1){
					p.sendMessage("§9Velocidade> §cO valor maximo da velocidade é 1");
					return true;
				}
				if(speed < -5){
					p.sendMessage("§9Velocidade> §cUse velocidades maiores que -5");
					return true;
				}
				if(p.isFlying())
					p.setFlySpeed(speed);
				else
					p.setWalkSpeed(speed);
				if(speed == 0.2)
					p.sendMessage("§9Velocidade> §eVocê está normal agora!");

				else
					p.sendMessage("§9Velocidade> §eVocê está " + speed*10 + "x mais rapido agora!");

			}else if(args.length == 2){
				Player p = Bukkit.getPlayer(args[0]);
				if(p == null){
					sender.sendMessage("§cEste player não esta online!");
					return true;
				}
				float speed = Float.valueOf(args[1]);
				if(speed > 1){
					p.sendMessage("§9Velocidade> §cO valor maximo da velocidade é 1");
					return true;
				}
				if(speed < -5){
					p.sendMessage("§9Velocidade> §cUse velocidades maiores que -5");
					return true;
				}
				if(p.isFlying())
					p.setFlySpeed(speed);
				else
					p.setWalkSpeed(speed);
				if(speed == 0.2)
					p.sendMessage("§9Velocidade> §e" + p.getDisplayName() + " está normal agora!");
				else
					p.sendMessage("§9Velocidade> §e" + p.getDisplayName() + " está normal agora!");
			}				
			
		}
		return false;
	}

}
