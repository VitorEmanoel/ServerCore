package Bungee.Commands;

import Bungee.Main;
import Bungee.Utils.Connect;
import Bungee.Utils.SimplesMessageAPI;
import Java.Server;
import Java.Servers;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerConnect extends Command{
	
	public ServerConnect(){
		super("sala");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		SimplesMessageAPI p = new SimplesMessageAPI(sender);
		if(!(sender instanceof ProxiedPlayer)){
			sender.sendMessage(p.msg("§9Sala> §cSomente players podem usar isto!"));
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer)sender;
		if(args.length == 0){
			p.send("§9Sala> §fVocê está na sala §e" + player.getServer().getInfo().getName());
			p.send("§9Sala> §eUse §f/servidor (servidor)");
			p.send("§9Sala> §eExemplo: §f/servidor Lobby-1");
		}else if(args.length == 1){
			String server = args[0];
			if(server.equalsIgnoreCase(player.getServer().getInfo().getName())){
				p.send("§9Sala> §cVocê está conectando à este servidor");
				return;
			}
			if(!server.contains("-")){
				if(player.getServer().getInfo().getName().split("-")[0].equalsIgnoreCase(server)){
					p.send("§9Sala> §cVocê já está em um " + server);
					return;
				}
				p.send("§9Sala> §cVocê não especificou o servidor que deseja se conectar.");
				p.send("§9Sala> §cVou tentar colocar você em §f" + server);
				String result = Connect.search(server);
				if(result == null){
					p.send("§9Sala> §cNão consegui achar o servidor §f" + server);
					return;
				}
				p.send("§9Sala> §fEnviando você ate §e" + result);
				player.connect(Main.pl.getProxy().getServerInfo(server));
				return;
			}
			Server sv = Servers.get(server);
			if(sv == null || !sv.hasOnline()){
				p.send("§9Sala> §cNão consegui achar o servidor §f" + server);
				return;
			}
			p.send("§9Sala> §fEnviando você ate §e" + sv.getName());
			player.connect(Main.pl.getProxy().getServerInfo(sv.getName()));
		}
	}

}
