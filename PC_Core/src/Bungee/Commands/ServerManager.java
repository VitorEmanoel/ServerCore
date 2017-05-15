package Bungee.Commands;

import java.io.File;
import java.io.IOException;

import Bungee.Main;
import Bungee.Utils.SimplesMessageAPI;
import Java.Server;
import Java.Servers;
import Java.Network.ProxyManager;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ServerManager extends Command{
	
	public ServerManager() {
		super("server");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		SimplesMessageAPI p = new SimplesMessageAPI(sender);
		if(args.length == 0){
			p.send("§e§l§m===============§3§l[ §9Servidores §3§l]§e§l§m===============§0");
			p.send("");
			for(ServerInfo sv : Main.pl.getProxy().getServers().values()){
				p.send("- " + sv.getName());
			}
			p.send("");
			p.send("§e§l§m===============§3§l[ §9Servidores §3§l]§e§l§m===============§0");
		}else if(args.length == 1){
			String server = args[0];
			if(!isServer(server)){
				p.send("§9Servidor> §cNão existe nenhum servidor com nome de §e" + server);
				return;
			}
			Server sv = Servers.get(server);
			String status = "§4§lOFFLINE";
			if(sv != null){
				if(sv.hasOnline()){
					status = "§e§lONLINE";
				}
			}
			String name = (sv == null) ? server : sv.getName();
			String onlines = (sv == null) ? "0" : sv.getOnlines() + "";
			String ip = (sv == null) ? "Impossivel conseguir o IP" : sv.getAddres().getHostString() + ":" + sv.getAddres().getPort();
			p.send("§e§l§m===============§3§l[ §9" + server + " §3§l]§e§l§m===============§0");
			p.send("Nome do servidor | Status | Players online | IP");
			p.send("- §3" + name + " §f| " + status + " §f| §3" + onlines + " §f| §3" + ip);
			if(sender instanceof ProxiedPlayer){
				p.send("");
				p.send("§3Controles:");
				p.send("§3- §e§l[Ligar]",HoverEvent.Action.SHOW_TEXT, "§eClique aqui para ligar o servidor " + server + "\n§fStatus: " + status,  ClickEvent.Action.RUN_COMMAND, "/server " + server + " ligar");
				p.send("§3- §4§l[Desligar]",HoverEvent.Action.SHOW_TEXT, "§eClique aqui para desligar o servidor " + server + "\n§fStatus: " + status,  ClickEvent.Action.RUN_COMMAND, "/server " + server + " desligar");
			}

			
		}else if(args.length == 2){
			if(args[1].equalsIgnoreCase("ligar")){
				String server = args[0];
				if(!isServer(server)){
					p.send("§9Servidor> §cNão existe nenhum servidor com nome de §e" + server);
					return;
				}
				Server sv = Servers.get(server);
				if(sv != null){
					if(sv.hasOnline()){
						p.send("§9Servidor> §cEsse servidor já está online!");
						return;
					}
				}
				ligar(server);
				p.send("§9Servidor> §fLigando o servidor " + server + "...");
			}else if(args[1].equalsIgnoreCase("desligar")){
				String server = args[0];
				if(!isServer(server)){
					p.send("§9Servidor> §cNão existe nenhum servidor com nome de §e" + server);
					return;
				}
				Server sv = Servers.get(server);
				if(sv != null){
					if(!sv.hasOnline()){
						p.send("§9Servidor> §cEsse servidor já está offline!");
						return;
					}
				}else{
					p.send("§9Servidor> §cEsse servidor já está offline!");
					return;
				}
				File file = new File(server + ".bat");
				if(!file.exists()){
					p.send("§9Servidor> §cNão encontrei o iniciador do servidor!");
					return;
				}
				ProxyManager.shutdown(sv);
				p.send("§9Servidor> §fDesligando o servidor " + server + "...");
			}
		}
		
	}
	
	private boolean isServer(String server){
		for(String key : Main.pl.getProxy().getServers().keySet()){
			if(server.equals(key)){
				return true;
			}
		}
		return false;
	}
	
	private void ligar(String server){
		try {
			Runtime.getRuntime().exec("cmd /c start " + server + ".bat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
