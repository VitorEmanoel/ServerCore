package Bungee.PermissionSystem;

import Bungee.Main;
import Java.Group;
import Java.Profile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PermissionCommands extends Command{
	
	public PermissionCommands() {
		super("group");
	}
	
	String preffix = "§9Grupo> ";
	
	private BaseComponent[] msg(String msg){
		return new ComponentBuilder(msg).create();
	}
	
	private void send(CommandSender sender, String msg){
		sender.sendMessage(new ComponentBuilder(msg).create());
	}
	
	private void send(CommandSender sender, String msg, HoverEvent.Action action ,String tooltip){
		if(!(sender instanceof ProxiedPlayer))return;
		TextComponent text = new TextComponent(TextComponent.fromLegacyText(msg));
		text.setHoverEvent(new HoverEvent(action, msg(tooltip)));
		sender.sendMessage(text);
	}
	
	private void sendArgs(CommandSender sender){
		if(sender instanceof ProxiedPlayer){
			send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
			send(sender, "§f/group <player>", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para ver\n"
																		+ "§eas informações do jogador");
			
			send(sender, "§f/group groups", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para ver\n"
																		+ "§etodos os grupos do servidor");
			
			send(sender, "§f/group servers", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para ver\n"
																		+ "§etodos os servidores do servidor");
			
			send(sender, "§f/group <player> add <group>", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para \n"
																		+ "§eadicionar o jogador a um grupo no servidor que você esta");

			send(sender, "§f/group <player> add <group> <server>", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para \n"
																		+ "§eadicionar o jogador a um grupo de um servidor especifico");
			
			send(sender, "§f/group <player> remove", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para \n"
																		+ "§eremover o jogador de um grupo no servidor que você esta");
			
			send(sender, "§f/group <player> remove <server>", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para \n"
																		+ "§eremover o jogador de um grupo de um servidor especifico");
			
			send(sender, "§f/group <player> move <group>", HoverEvent.Action.SHOW_TEXT, "§eUse este comando para\n"
																		+ "§emover o jogador para um grupo no servidor que você esta");
			
			send(sender, "§f/group <player> move <group> <server>", HoverEvent.Action.SHOW_TEXT, "§eUse esse comando para\n"
																		+ "§eadicionar o jogador a um grupo de um servidor especifico");
			send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
		}else{
			send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
			send(sender, "§f/group <player>");
			
			send(sender, "§f/group groups");
			
			send(sender, "§f/group servers");

			send(sender, "§f/group <player> add <group>");
			
			send(sender, "§f/group <player> add <group> <server>");
			
			send(sender, "§f/group <player> remove");
			
			send(sender, "§f/group <player> remove <server>");
			
			send(sender, "§f/group <player> move <group>");
			
			send(sender, "§f/group <player> move <group> <server>");
			send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
		}
	}
	
	private void sendInfo(CommandSender sender, CommandSender sender2){
		if(sender2 instanceof ProxiedPlayer){
			Profile p = Profile.get(((ProxiedPlayer) sender2).getUniqueId());
			send(sender, "§e§m================§e§l[§9" + p.getName() + "§e§l]§e§m================");
			send(sender, "§fNome: " + p.getName());
			send(sender, "§fNome falso: " + p.getFakeName());
			send(sender, "§fIP: " + ((ProxiedPlayer)sender2).getAddress().getAddress().getHostAddress());
			send(sender, "§fPing: " + ((ProxiedPlayer)sender2).getPing());
			send(sender, " ");
			send(sender, "§fServidor | Grupos");
			for(String svgp : p.getServerGroupList()){
				String[] guide = svgp.split(":");
				send(sender, "- " + guide[0] + " | " + guide[1]);
			}
			send(sender, "§e§m================§e§l[§9" + p.getName() + "§e§l]§e§m================");
		}
	}
	
	private void sendInfo(CommandSender sender, Profile prof){
			Profile p = prof;
			send(sender, "§e§m================§e§l[§9" + p.getName() + "§e§l]§e§m================");
			send(sender, "§fNome: " + p.getName());
			send(sender, "§fNome falso: " + p.getFakeName());
			send(sender, " ");
			send(sender, "§fServidor | Grupos");
			for(String svgp : p.getServerGroupList()){
				String[] guide = svgp.split(":");
				send(sender, "- " + guide[0] + " | " + guide[1]);
			}
			send(sender, "§e§m================§e§l[§9" + p.getName() + "§e§l]§e§m================");
	}
	
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer){
			Profile p = Profile.get(((ProxiedPlayer) sender).getUniqueId());
			if(!p.hasPermission(Group.OWNER, ((ProxiedPlayer) sender).getServer().getInfo().getName())){
				send(sender, preffix +"§cVocê não tem permissão para fazer isto!");
				return;
			}
		}
		if(args.length == 0){
			sendArgs(sender);
		}else if(args.length == 1){
			if(args[0].equalsIgnoreCase("groups")){
				send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
				send(sender, "§fNome|Grupo");
				for(Group group : Group.values()){
					send(sender, "- " + group.getName() + " | " + group.toString().toUpperCase());
				}
				send(sender, "§e§m================§e§l[§9Grupos§e§l]§e§m================");
			}else if(args[0].equalsIgnoreCase("servers")){
				send(sender, "§e§m================§e§l[§9Servidores§e§l]§e§m================");
				for(ServerInfo sv : Main.pl.getProxy().getServers().values()){
					send(sender, "- " + sv.getName());
				}
				send(sender, "§e§m================§e§l[§9Servidores§e§l]§e§m================");
			}else{
				ProxiedPlayer p = Main.pl.getProxy().getPlayer(args[0]);
				if(p != null){
					sendInfo(sender, p);
				}else{
					Profile prof = Profile.get(args[0]);
					if(prof == null){
						send(sender, preffix +"§cEste player não está cadastrado no servidor!");
						return;
					}
					sendInfo(sender, prof);
				}
			}
		}else if(args.length == 2){
			if(!(sender instanceof ProxiedPlayer)){
				send(sender, preffix +"§cPor favor especifique o servidor");
				return;
			}
			String server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
			Profile p = Profile.get(args[0]);
			if(args[1].equalsIgnoreCase("remove")){
				if(p == null){
					send(sender, "§cEste player não está cadastrado no servidor!");
					return;
				}
				if(p.getGroup(server) == null){
					send(sender, "§cEste player não esta em nenhum grupo deste servidor!");
					return;
				}
				p.removeGroup(server);
				send(sender, preffix +"§fVocê removeu todos os grupos de §e" + p.getName() + " §fdo servidor §e" + server);
			}else
			sendArgs(sender);
		}else if(args.length == 3){
			try{
				Profile p = Profile.get(args[0]);
				String serverd = args[2];
				if(args[1].equalsIgnoreCase("remove")){
					if(p == null){
						send(sender, "§cEste player não está cadastrado no servidor!");
						return;
					}
					if(p.getGroup(serverd) == null){
						send(sender, preffix +"§cEste player não esta em nenhum grupo deste servidor!");
						return;
					}
					p.removeGroup(serverd);
					send(sender, preffix +"§fVocê removeu todos os grupos de §e" + p.getName() + " §fdo servidor §e" + serverd);
					return;
				}
				String server = ((ProxiedPlayer)sender).getServer().getInfo().getName();
				Group group = Group.valueOf(args[2].toUpperCase());
				if(!(sender instanceof ProxiedPlayer)){
					send(sender, preffix +"§cPor favor especifique o servidor!");
					return;
				}
				if(args[1].equalsIgnoreCase("move")){
					if(p == null){
						send(sender, "§cEste player não está cadastrado no servidor!");
						return;
					}
					if(p.getGroup(server) == null){
						send(sender, preffix +"§cEste player não esta em nenhum grupo deste servidor!");
						return;
					}
					if(p.getGroup(server) == group){
						send(sender, preffix + "§cEste player já esta no grupo §e" + group.getName() + " §cno servidor §e" + server);
						return;
					}
					p.addGroup(server, group);
					send(sender, preffix + "§fVocê moveu §e" + p.getName() + " §fpara o grupo §e" + group.getName() + " §fno servidor §e" + server);
				}else if(args[1].equalsIgnoreCase("add")){
					if(p == null){
						send(sender, "§cEste player não está cadastrado no servidor!");
						return;
					}
					if(p.getGroup(server) != null){
						send(sender, preffix + "§cEste player já esta em um grupo deste servidor!");
						return;
					}
					p.addGroup(server, group);
					send(sender, preffix + "§fVocê adicionou §e" + p.getName() + " §fpara o grupo §e" + group.getName() + " §fno servidor §e" + server);
				}else{
					sendArgs(sender);
				}
			}catch(IllegalArgumentException e){
				send(sender, preffix +"§cO grupo digitado não existe!");
			}
		}else if(args.length == 4){
			try{
				String server = args[3];
				Group group = Group.valueOf(args[2].toUpperCase());
				Profile p = Profile.get(args[0]);
				if(args[1].equalsIgnoreCase("move")){
					if(!server.contains("-") && !server.equals("*")){
						send(sender, preffix +"§cSintaxe do servidor incorreta");
						send(sender, preffix +"§eExemplo: §fLobby-1 ou Lobby-*");
						return;
					}
					if(!isServer(server)){
						send(sender, preffix +"§cNão à nenhum servidor com nome de " + server);
						return;
					}
					if(p == null){
						send(sender, "§cEste player não está cadastrado no servidor!");
						return;
					}
					if(p.getGroup(server) == group){
						send(sender, preffix +"§cEste player já esta no grupo §e" + group.getName() + " §cno servidor §e" + server);
						return;
					}
					if(p.getGroup(server) == null){
						send(sender, preffix +"§cEste player não esta em nenhum grupo deste servidor!");
						return;
					}
					p.addGroup(server, group);
					send(sender, preffix +"§fVocê moveu §e" + p.getName() + " §fpara o grupo §e" + group.getName() + " §fno servidor §e" + server);
				}else if(args[1].equalsIgnoreCase("add")){
					if(!server.contains("-") && !server.equals("*")){
						send(sender, preffix +"§cSintaxe do servidor incorreta");
						send(sender, preffix +"§eExemplo: Lobby-1 ou Lobby-*");
						return;
					}
					if(!isServer(server)){
						send(sender, preffix +"§cNão à nenhum servidor com nome de " + server);
						return;
					}
					if(p == null){
						send(sender, "§cEste player não está cadastrado no servidor!");
						return;
					}
					if(p.getGroup(server) != null){
						send(sender, preffix +"§cEste player já esta em nenhum grupo deste servidor!");
						return;
					}
					p.addGroup(server, group);
					send(sender, preffix +"§fVocê adicionou §e" + p.getName() + " §fpara o grupo §e" + group.getName() + " §fno servidor §e" + server);
				}else if(args[1].equalsIgnoreCase("remove")){
					
				}else{
					sendArgs(sender);}
				
			}catch(IllegalArgumentException e){
				send(sender, preffix +"§cO grupo digitado não existe!");}
			
		}else{
			sendArgs(sender);}
	}
	
	private boolean isServer(String server){
		for(ServerInfo sv : Main.pl.getProxy().getServers().values()){
			if(server.split("-")[0].equals(sv.getName().split("-")[0])){
				if(server.split("-")[1].equals(sv.getName().split("-")[1]) || server.split("-")[1].equals("*"))
					return true;
			}
		}
		return false;
	}
	

}
