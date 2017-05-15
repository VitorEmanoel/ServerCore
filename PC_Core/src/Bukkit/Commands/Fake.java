package Bukkit.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import Java.Profile;


public class Fake implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("fake")){
			if(!(sender instanceof Player)){
				sender.sendMessage("§9Fake> §fVocê não pode mudar seu nick pelo console!");
				return false;
			}
			Player p = (Player)sender;
			if(args.length != 0){
				p.sendMessage("§9Fake> §eUse §f/fake (nick)");
				return false;
			}else{
				String fake = args[0];
				if(fake.length() > 16){
					p.sendMessage("§9Fake> §fO nick so pode ter 16 caracteres!");
					return false;
				}
				Profile pm = Profile.get(sender.getName());
				pm.setFakeName(fake);
				p.sendMessage("§9Fake> §fVocê alterou seu nick para §e" + fake);
			}
				
		}
		return false;
	}

}
