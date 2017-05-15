package Java;

import java.util.Collection;
import java.util.HashMap;

public class Servers {
	
	private static HashMap<String, Server> servers = new HashMap<>();
	
	public static void register(Server sv){
		servers.put(sv.getName(), sv);
	}
	
	public static void unregister(Server sv){
		servers.remove(sv.getName());
	}
	
	public static Server get(String server){
		return servers.get(server);
	}
	
	public static Collection<Server> getServers(){
		return servers.values();
	}
	

}
