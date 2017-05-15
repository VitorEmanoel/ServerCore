package Java.Network;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import Java.Server;
import Java.Servers;
import Java.Status;

public class ProxyManager {
	
	public static void loadServer(String text, Socket socket){
		try{
			String[] data = text.split("-");
			String name = data[0] + "-" +data[1];
			String ip = data[2];
			Status action = Status.valueOf(data[3].toUpperCase());
			int max = Integer.valueOf(data[4]);
			Server server = Servers.get(name);
			if(server == null){
				server = new Server(name, ip.split(":")[0], Integer.parseInt(ip.split(":")[1]));
			}
			server.setStatus(action);
			server.setMaxPlayers(max);
			server.setSocket(socket);
			server.setPrintWriter(new PrintWriter(socket.getOutputStream()));
			Servers.register(server);
			System.out.println("[Proxy] O servidor " + name + " foi " + action.toString() + " agora!" );
			System.out.println("[Proxy] IP: " + ip);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void freeze(Server sv, String player){
		PrintWriter pw = sv.getPrintWriter();
		System.out.println("[Proxy] Congelando player " + player + " no servidor " + sv.getName());
		pw.println("nomove-" + player);
		pw.flush();
	}
	
	public static void unfreeze(Server sv, String player){
		PrintWriter pw = sv.getPrintWriter();
		System.out.println("[Proxy] Descongelando player " + player + " no servidor " + sv.getName());
		pw.println("move-" + player);
		pw.flush();
	}
	
	public static void shutdown(Server sv){
		PrintWriter pw = sv.getPrintWriter();
		System.out.println("[Proxy] Desligando o servidor " + sv.getName());
		pw.println("off");
		pw.flush();
	}

}
