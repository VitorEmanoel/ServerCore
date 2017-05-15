package Bungee.Commands;

import Bungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class Restart extends Command{
	
	public Restart() {
		super("restart");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)){
			sender.sendMessage(new ComponentBuilder("Reiniciando servidor").create());
			Main.pl.getProxy().stop();
		}
		
	}

}
