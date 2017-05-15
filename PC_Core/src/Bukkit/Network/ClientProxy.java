package Bukkit.Network;

import org.bukkit.Bukkit;

import Bukkit.Main;
import Java.Status;

public class ClientProxy implements Runnable {

	private Network rede;
	private Status status;
	public ClientProxy(Network rede, Status status) {
		this.rede = rede;
		this.status = status;
	}
	
	@Override
	public void run() {
		switch (status) {
		case LIGADO:
			String msg = Main.servername + "-" + Bukkit.getIp() + ":" + Bukkit.getPort() + "-ligado-" + Bukkit.getMaxPlayers();
			rede.send(msg);
			break;
		case DESLIGADO:
			String msg2 = Main.servername + "-" + Bukkit.getIp() + ":" + Bukkit.getPort() + "-desligado-" + Bukkit.getMaxPlayers();
			rede.send(msg2);
			rede.send("Desconectar");
			rede.getPW().close();
			break;
		}
	}

}
