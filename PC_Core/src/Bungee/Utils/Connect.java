package Bungee.Utils;

import Java.Server;
import Java.Servers;

public class Connect {


	public static String search(String server) {
		String result = null;
		for(Server sv : Servers.getServers()){
			if(sv.getName().startsWith(server)){
				if(sv.hasOnline()){
					int onlines = sv.getOnlines();
					int max = sv.getMaxPlayers();
					if(max > onlines){
						result = sv.getName();
					}
				}
			}
		}
		return result;
	}
}
