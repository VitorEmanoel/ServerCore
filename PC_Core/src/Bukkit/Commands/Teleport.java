package Bukkit.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import Bukkit.Main;
import Bukkit.Utils.API;
import Java.Group;
import Java.Profile;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Teleport implements CommandExecutor{
	
	private List<UUID> tpoff = new ArrayList<UUID>();
	private HashMap<UUID, Player> tpa = new HashMap<>();
	private HashMap<UUID, BukkitTask> timecontrol = new HashMap<>();
	
	
	private void applyEffect(Player p){
		double radius = 1;
		double y2 = 0.0;
		double y22 = 2.0;
		for(double i = 0; i <= 23.7 ;i+= 0.05) {
			double x2 = radius * Math.sin(i);
			double z2 = radius * Math.cos(i);
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(y2 < 2){
				p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, new Location(p.getWorld(), p.getLocation().getX() + x2, p.getLocation().getY() + y2, p.getLocation().getZ()+z2), 1);
				y2 += 0.01;
			}else{
				if(y22 > 0){
					y22 = y22 - 0.01;
					p.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, new Location(p.getWorld(), p.getLocation().getX() + x2, p.getLocation().getY() + y22, p.getLocation().getZ()+z2), 1);
				}
			}

		}
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("tp")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				Profile pm = Profile.get(sender.getName());
				if(pm.hasPermission(Group.HELPER, Main.servername)){
					if(args.length == 0){
						p.sendMessage("§9Teleporte> §eUse §f/tp (Player) ou /tp (X) (Y) (Z)");
						p.sendMessage("§9Teleporte> §fAliases: /ir");
						new BukkitRunnable() {
							
							@Override
							public void run() {
								applyEffect(p);
							}
						}.runTaskTimerAsynchronously(Main.pl, 0, 20*16);
						return true;
					}
					if(args.length == 1){
						Player p2 = Bukkit.getPlayer(args[0]);
						if(p2 == p){
							p.sendMessage("§9Teleporte> §cVocê não pode teleportar para si mesmo!");
							return true;
						}
						if(p2 == null){
							p.sendMessage("§9Teleporte> §cEste player não esta online!");
							return true;
						}
						p.teleport(p2);
						p.sendMessage("§9Teleporte> §fVocê foi ate §e" + p2.getDisplayName());
					}
					if(args.length == 3){
						try{
							double x = Double.parseDouble(args[0]);
							double y = Double.parseDouble(args[1]);
							double z = Double.parseDouble(args[2]);
							p.teleport(new Location(p.getWorld(), x, y, z));
							p.sendMessage("§9Teleporte> §fVocê foi teleportado para as coordenadas X/Y/Z: " + x + "/" + y + "/" + z);
						}catch(NumberFormatException e){
							p.sendMessage("§9Teleporte> §eUse §f/tp (X) (Y) (Z)");
						}
					}
				}else{
					sender.sendMessage("§cVocê não tem permissão para fazer isto!");
				}
			}else{
				sender.sendMessage("§cIsto so pode ser usado por players!");
			}
		}
		if(cmd.getName().equalsIgnoreCase("tph")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(p.hasPermission("projectcraft.tph")){
					if(args.length == 0){
						p.sendMessage("§9Teleporte> §eUse §f/tph (Player)");
						p.sendMessage("§9Teleporte> §fAliases: /trazer (Player)");
						return true;
					}
					if(args.length == 1){
						Player p2 = Bukkit.getPlayer(args[0]);
						if(p2 == p){
							p.sendMessage("§9Teleporte> §cVocê não pode puxar si mesmo!");
							return true;
						}
						if(p2 == null){
							p.sendMessage("§9Teleporte> §cEste player não esta online!");
							return true;
						}
						p2.teleport(p);
						p.sendMessage("§9Teleporte> §fVocê levou §e" + p2.getDisplayName() + " §fate você!");
					}
				}else{
					sender.sendMessage("§cVocê não tem permissão para fazer isto!");
				}
			}else{
				sender.sendMessage("§cIsto so pode ser usado por players!");
			}
		}
		if(cmd.getName().equalsIgnoreCase("tpa")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				if(p.hasPermission("projectcraft.tpa")){
					if(args.length == 0){
						p.sendMessage("§9Teleporte> §eUse §f/tpa (<Player>|aceitar|negar)");
					}
					if(args.length == 1){
						if(args[0].equalsIgnoreCase("aceitar")){
							if(!hasRequest(p)){
								p.sendMessage("§9Teleporte> §cVocê não tem pedidos de teleporte!");
								return true;
							}
							acceptTpa(p);
							return true;
						}else if(args[0].equalsIgnoreCase("negar")){
							if(!hasRequest(p)){
								p.sendMessage("§9Teleporte> §cVocê não tem pedidos de teleporte!");
								return true;
							}
							declineTpa(p);
							return true;
						}else if(args[0].equalsIgnoreCase("on")){
							if(!tpoff.contains(p.getUniqueId())){
								p.sendMessage("§9Teleporte> §cVocê ja esta com os pedidos de teleporte ativado!");
								return true;
							}
							tpoff.remove(p.getUniqueId());
							p.sendMessage("§9Teleporte> §fVocê ativo os pedidos de teleporte!");
							return true;
						}else if(args[0].equalsIgnoreCase("off")){
							if(tpoff.contains(p.getUniqueId())){
								p.sendMessage("§9Teleporte> §cVocê ja esta com os pedidos de teleporte desativado!");
								return true;
							}
							tpoff.add(p.getUniqueId());
							p.sendMessage("§9Teleporte> §fVocê desativou os pedidos de teleporte!");
							return true;
						}else{
							Player p2 = Bukkit.getPlayer(args[0]);
							if(p2 == p){
								p.sendMessage("§9Teleporte> §cVocê não pode enviar um pedido de teleporte para si mesmo!");
								return true;
							}
							if(p2 == null){
								p.sendMessage("§9Teleporte> §cEste player n§o esta online!");
								return true;
							}
							if(tpoff.contains(p2.getUniqueId())){
								p.sendMessage("§9Teleporte> §cEste player esta com os pedidos de teleporte desativado!");
								return true;
							}
							sendTpa(p, p2);
							p.sendMessage("§9Teleporte> §fPedido de teleporte para §e" + p2.getDisplayName() + " §fenviado com sucesso!");
						}
					}
				}else{
					sender.sendMessage("§cVocê não tem permiss§o para fazer isto!");
				}
			}else{
				sender.sendMessage("§cIsto so pode ser usado por players!");
			}
		}
		return false;
	}
	
	private void sendTpa(Player p, Player p2){
		tpa.put(p2.getUniqueId(), p);
		BukkitTask task = new BukkitRunnable() {
			
			@Override
			public void run() {
				p2.sendMessage("§9Teleporte> §fO pedido de teleporte de §e" + p2.getDisplayName() + " §fteve o tempo esgotado!");
				p.sendMessage("§9Teleporte> §fO seu pedido de teleporte para " + p2.getDisplayName() + " §fteve o tempo esgotado!");
			}
		}.runTaskLater(Main.pl, 20*120);
		TextComponent tp = new TextComponent("§9Teleporte> ");
		TextComponent space = new TextComponent("   ");
		space.setHoverEvent(null);
		TextComponent accept = new TextComponent("§9[Aceitar]");
		accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Clique aqui, §fpara aceitar o pedido de teleporte de §e" + p.getDisplayName() + "\n\n§bVocê tambem pode usar §f/tpa aceitar").create()));
		accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa aceitar"));
		TextComponent decline = new TextComponent("§4[Negar]");
		
		decline.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§3Clique aqui, §fpara negar o pedido de teleporte de §e" + p.getDisplayName() + "\n\n§bVocê tambem pode usar §f/tpa negar").create()));
		decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa negar"));
		tp.addExtra(accept);
		tp.addExtra(space);
		tp.addExtra(decline);
		p2.sendMessage("§9Teleporte> §fVocê recebeu um pedido de teleporte de §e" + p.getDisplayName() + "§f!");
		API.sendActionBar(p2, "§9Teleporte> §eVocê recebeu um pedido de teleporte!");
		p2.spigot().sendMessage(tp);
		timecontrol.put(p2.getUniqueId(), task);
	}
	
	private void declineTpa(Player p){
		timecontrol.get(p.getUniqueId()).cancel();
		timecontrol.remove(p.getUniqueId());
		Player p2 = tpa.get(p.getUniqueId());
		p2.sendMessage("§9Teleporte> §e" + p.getDisplayName() + " §fnegou o seu pedido de teleporte!");
		p.sendMessage("§9Teleporte> §fVocê negou o pedido de teleporte de §e" + p2.getDisplayName());
		tpa.remove(p.getUniqueId());
	}
	
	private void acceptTpa(Player p){
		timecontrol.get(p.getUniqueId()).cancel();
		timecontrol.remove(p.getUniqueId());
		Player p2 = tpa.get(p.getUniqueId());
		p2.sendMessage("§9Teleporte> §e" + p.getDisplayName() + " §faceito o seu pedido de teleporte!");
		p.sendMessage("§9Teleporte> §fVocê aceitou o pedido de teleporte de §e" + p2.getDisplayName());
		p2.teleport(p);
		tpa.remove(p.getUniqueId());
	}
	
	private boolean hasRequest(Player p){
		if(tpa.containsKey(p.getUniqueId())){
			return true;
		}
		return false;
	}

}
