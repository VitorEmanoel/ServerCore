package Bukkit.Commands;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import Bukkit.Main;
import Java.Group;
import Java.Profile;
import net.minecraft.server.v1_11_R1.EntityPlayer;
import net.minecraft.server.v1_11_R1.PacketPlayOutCamera;

public class Spy implements CommandExecutor{
	
	private HashMap<UUID, Player> spy = new HashMap<>();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("spy")){
			if(sender instanceof Player){
				Player p = (Player)sender;
				Profile pm = Profile.get(sender.getName());
				if(pm.hasPermission(Group.HELPER, Main.servername)){
					switch (args.length) {
					case 0:
						p.sendMessage("§9Espião> §eUse §f/spy (<Player>|quit|sair)");
						p.sendMessage("§9Espião> §fAliases: §f/espiar");
						break;
					case 1:
						if(args[0].equalsIgnoreCase("sair")||args[0].equalsIgnoreCase("quit")){
							if(!spy.containsKey(p.getUniqueId())){
								p.sendMessage("§9Espião> §cVocê não esta espiando ninguem!");
								return true;
							}
							Player p2 = spy.get(p.getUniqueId());
							PacketPlayOutCamera packet = new PacketPlayOutCamera();
							packet.a = p.getEntityId();
							EntityPlayer ep = ((CraftPlayer)p).getHandle();
							ep.playerConnection.sendPacket(packet);
							p.setGameMode(GameMode.SURVIVAL);
							p.sendMessage("§9Espiâo> §fVocê parou de espiar §e" + p2.getDisplayName() + "§f!");
							spy.remove(p.getUniqueId());
							return true;
						}else{
							Player p2 = Bukkit.getPlayer(args[0]);
							if(p2 == p){
								p.sendMessage("§9Espião> §cVocê não pode espiar você mesmo!");
								return true;
							}
							if(p2 == null){
								p.sendMessage("§9Espião> §cEste player não esta online!");
								return true;
							}
							if(spy.containsKey(p.getUniqueId())){
								Player p2c = spy.get(p.getUniqueId());
								p.sendMessage("§9Espião> §cVocê está espiando §e" + p2c.getDisplayName() + ". §fUse /espiar sair");
								return true;
							}
							PacketPlayOutCamera cm = new PacketPlayOutCamera();
							cm.a = p2.getEntityId();
							EntityPlayer ep = ((CraftPlayer)p).getHandle();
							p.setGameMode(GameMode.SPECTATOR);
							ep.playerConnection.sendPacket(cm);
							spy.put(p.getUniqueId(), p2);
							p.sendMessage("§9Espião> §fAgora você está espiando §e" + p2.getDisplayName() + "§f!");
						}
						break;
					default:
						p.sendMessage("§9Espião> §eUse §f/spy (<Player>|quit|sair)");
						p.sendMessage("§9Espião> §fAliases: §f/espiar");
						break;
					}
				}else{
					sender.sendMessage("§cVocê não tem permissão para fazer isto!");
				}
			}else{
				sender.sendMessage("§cIsto so pode ser feito por players!");
			}
		}
		return false;
	}

}
